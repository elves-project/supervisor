package cn.gyyx.elves.util.mq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.SecurityUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @ClassName: MessageProducer
 * @Description: 消息发送producer
 * @author East.F
 * @date 2016年10月31日 上午2:31:14
 */
@Component
public class MessageProducer {

	@Autowired
	private AmqpTemplate topicTemplate;
	
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
		String directRoutingKey =SecurityUtil.getUniqueKey();
		
		Map<String,Object> sendMsg = new HashMap<String,Object>();
		sendMsg.put("mqkey",topicRoutingKey+"."+serverName);
		sendMsg.put("mqtype","call."+directRoutingKey);
		sendMsg.put("mqbody",bodyMsg==null?new HashMap<String,Object>():bodyMsg);
		
		String topicMessage = JSON.toJSONString(sendMsg);
		
		Thread thread =null;
		RpcDirectConsumer consumer=null;
		try {
			consumer=new RpcDirectConsumer(directRoutingKey,Thread.currentThread());
			//启动子线程，等待direct 通知
			thread = new Thread(consumer);
			thread.start();
			LOG.debug("call message:"+topicMessage+",topicRoutingKey:"+topicRoutingKey);
			topicTemplate.convertAndSend(topicRoutingKey,topicMessage);
			Thread.sleep(outTimeMillis);
		} catch (InterruptedException e) {
			//主线程sleep outTimeMillis,中间随时会被唤醒，抛出InterruptedException 忽略
			LOG.debug("receive call back data , main thread is interrupted");
		} catch (IOException e1) {
			LOG.error(ExceptionUtil.getStackTraceAsString(e1));
			throw new Exception("connect to rabbitmq fail");
		}finally{
			//如果thread 还在阻塞，发出中断信号interrupt ,结束线程
			if(null!=thread){
				thread.interrupt();
			}
		}
		if(null!=consumer.getCallBackMessage()&&null!=consumer.getCallBackMessage().get("mqbody")){
			try {
				return JSON.parseObject(consumer.getCallBackMessage().get("mqbody").toString(),new TypeReference<Map<String, Object>>(){});
			} catch (Exception e) {
				LOG.error("rabbitmq response data error:"+ExceptionUtil.getStackTraceAsString(e));
				throw new Exception("rabbitmq response data error");
			}
		}else{
			throw new Exception("waitting rabbitmq response timeout");
		}
		
	}
}
