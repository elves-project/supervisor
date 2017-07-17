package cn.gyyx.elves.util.mq;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;

import cn.gyyx.elves.util.DateUtils;
import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.SpringUtil;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: MessageProcesserThread
 * @Description: rabbitmq消息处理器
 * @author East.F
 * @date 2017年5月27日 上午10:48:46
 */
public class MessageProcesserThread implements Runnable{
	
	private Logger LOG=Logger.getLogger(MessageProcesserThread.class);
	
	private MessageProducer messageProducer;
	
	private Message message;
	
	private  final String SERVICE_NAME = "elvesConsumerService";
	
	public MessageProcesserThread(MessageProducer messageProducer,Message message) {
		super();
		this.messageProducer = messageProducer;
		this.message=message;
	}
	
	@Override
	public void run() {
		LOG.info("MessageProcesserThread process message starttime : "+DateUtils.currentTimestamp2String("yyyy-MM-dd HH:mm:ss SSS"));
		try {
			byte [] body=message.getBody();
			if(null==body){
				return;
			}
			String bodyStr = new String(body,"UTF-8");
			
			//消息数据转化为ElvesMqMessage，验证数据结构是否符合标准
			ElvesMqMessage elvesMsg = ElvesMqMessage.getInstance(bodyStr);
			if(null==elvesMsg){
				LOG.error("Message structure error!");
				return;
			}
			
			//elves consumer 服务类进行消费消息
			Object serviceBean=SpringUtil.getBean(this.SERVICE_NAME);
			Class clazz = serviceBean.getClass();
	        Method method = clazz.getDeclaredMethod(elvesMsg.getAction(),Map.class);
	        Object result = method.invoke(serviceBean,elvesMsg.getMqbody());
	        
	        //消息处理结果为null ，或者cast类型的消息，后续不做处理
	        if(result==null||StringUtils.isBlank(elvesMsg.getDirectRoutingKey())){
	        	return;
	        }
	        Map<String,Object> back =new HashMap<String,Object>();
	        back.put("mqkey",elvesMsg.getToModule()+"."+elvesMsg.getFromModule());
	        back.put("mqflag","cast");
			back.put("mqbody",result);
			String responseMsg =JSON.toJSONString(back);
			LOG.info("Response msg :"+responseMsg);
			messageProducer.cast(elvesMsg.getDirectRoutingKey(), responseMsg);
		} catch (Exception e) {
			LOG.error(ExceptionUtil.getStackTraceAsString(e));
		}
		LOG.info("MessageProcesserThread process message endtime : "+DateUtils.currentTimestamp2String("yyyy-MM-dd HH:mm:ss SSS"));
	}
}
