package cn.gyyx.elves.util.mq;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gyyx.elves.util.ExceptionUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @ClassName: DirectRpcConsumer
 * @Description: rpc 点对点通讯，direct 消费者
 * @author East.F
 * @date 2016年11月3日 下午12:02:49
 */
public class RpcDirectConsumer extends MqConnecter implements Runnable {

	private Logger LOG = Logger.getLogger(RpcDirectConsumer.class);

	//回调数据
	private Map<String, Object> callBackMessage;
	
	//主线程
	private Thread mainProcess;

	private QueueingConsumer consumer;
	
	public RpcDirectConsumer(String routingKey, Thread mainProcess) throws IOException {
		this.mainProcess = mainProcess;
		super.init();
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, PropertyLoader.MQ_EXCHANGE, routingKey);
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, false, consumer);
	}

	@Override
	public void run() {
		try {
			// 线程阻塞
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			LOG.info("direct consumer reveive msg:" + message);
			callBackMessage =JSON.parseObject(message,new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			if(!(e instanceof InterruptedException)){
				LOG.error("RpcDirectConsumer error:"+ExceptionUtil.getStackTraceAsString(e));
			}
		}finally{
			//关闭连接
			super.close();
			// 如果主线程还在阻塞，则唤醒主线程
			LOG.debug("wake up mainProcess");
			if(null!=mainProcess){
				mainProcess.interrupt();
			}
		}
	}

	public Map<String, Object> getCallBackMessage() {
		return callBackMessage;
	}
	
}
