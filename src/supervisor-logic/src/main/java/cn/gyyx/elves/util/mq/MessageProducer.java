package cn.gyyx.elves.util.mq;

import cn.gyyx.elves.util.JsonFilter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MessageProducer
 * @Description: 消息发送producer
 * @author East.F
 * @date 2016年10月31日 上午2:31:14
 */
@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate topicTemplate;

    @Autowired
    private CachingConnectionFactory connectionFactory;


    private Logger LOG = Logger.getLogger(MessageProducer.class);

    /**
     * @Title: cast
     * @Description: cast类型发送消息
     * @param routingKey
     * @return void    返回类型
     */
    public void cast(String routingKey, String message){
        LOG.info("cast message:"+message+",routingKey:"+routingKey);
        topicTemplate.convertAndSend(routingKey, message);
    }


    /**
     * @Title: reply
     * @Description: reply消息
     * @param queueName
     * @return void    返回类型
     */
    public void reply(String queueName, String message){
        LOG.info("reply message:"+message+",queueName:"+queueName);
        topicTemplate.send("",queueName,new Message(message.getBytes(),new MessageProperties()));
    }

    /**
     * @Title: call
     * @Description: call类型，点对点发送接收消息
     * @param topicRoutingKey
     * @param serverName
     * @param bodyMsg
     * @param outTimeMillis
     * @throws Exception 设定文件
     * @return Map<String,Object>    返回类型
     */
    public Map<String,Object> call(String topicRoutingKey,String serverName,Map<String,Object> bodyMsg,int outTimeMillis) throws Exception{
        Map<String,Object> sendMsg = new HashMap<String,Object>();
        sendMsg.put("mqkey",topicRoutingKey+"."+serverName);
        sendMsg.put("mqtype","call");
        sendMsg.put("mqbody",bodyMsg==null?new HashMap<String,Object>():bodyMsg);

        String topicMessage = JSON.toJSONString(sendMsg, JsonFilter.filter);
        Message message=new Message(topicMessage.getBytes("UTF-8"),new MessageProperties());

        RabbitTemplate directTemplate =new RabbitTemplate();
        directTemplate.setConnectionFactory(connectionFactory);
        directTemplate.setExchange(PropertyLoader.MQ_EXCHANGE);
        directTemplate.setReplyTimeout(outTimeMillis);

        Message reply=directTemplate.sendAndReceive(topicRoutingKey,message);
        if(reply==null){
            throw new Exception("waitting rabbitmq response timeout");
        }
        String body=new String(reply.getBody(),"UTF-8");
        Map<String,Object> reqMsgMap =JSON.parseObject(body,new TypeReference<Map<String, Object>>(){});
        LOG.info("back :"+reqMsgMap);
        reqMsgMap =JSON.parseObject(reqMsgMap.get("mqbody").toString(),new TypeReference<Map<String, Object>>(){});
        return reqMsgMap;
    }
}
