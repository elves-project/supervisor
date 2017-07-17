package cn.gyyx.elves.util.mq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import cn.gyyx.elves.util.ExceptionUtil;

/**
 * @ClassName: MessageHandler
 * @Description: 订阅消息的接受控制器
 * @author East.F
 * @date 2016年10月31日 上午2:07:56
 */
public class MessageHandler implements MessageListener {
	
	private Logger LOG=Logger.getLogger(MessageHandler.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private MessageProducer messageProducer;
	
	@Override
	public void onMessage(Message message) {
		try {
			LOG.info("MessageHandler reveive mq message and submit to MessageProcesserThread："+message);
			taskExecutor.execute(new Thread(new MessageProcesserThread(messageProducer, message)));
		} catch (Exception e) {
			LOG.error("MessageHandler error ："+ExceptionUtil.getStackTraceAsString(e));
		}
		
	}
}
