package cn.gyyx.elves.util.mq;

import java.io.IOException;

import org.apache.log4j.Logger;

import cn.gyyx.elves.util.ExceptionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName: MqConnecter
 * @Description: mq连接父类
 * @author East.F
 * @date 2016年11月3日 下午12:04:30
 */
public class MqConnecter {
	
	private static final Logger LOG = Logger.getLogger(MqConnecter.class);
	
	protected Connection connection = null;
	
	protected Channel channel = null;
	
	protected ConnectionFactory factory=null;
	
	public void init() throws IOException{
		factory = new ConnectionFactory();
		factory.setHost(PropertyLoader.MQ_IP);
		factory.setPort(PropertyLoader.MQ_PORT);
		factory.setUsername(PropertyLoader.MQ_USER);
		factory.setPassword(PropertyLoader.MQ_PASSWORD);
		connection = factory.newConnection();
		channel = connection.createChannel();
	}
	
	public void close(){
		if(channel!=null&&channel.isOpen()){
			try {
				channel.close();
			} catch (IOException e) {
				LOG.error("close connection error:"+ExceptionUtil.getStackTraceAsString(e));
			}
		}
		if (connection != null&&connection.isOpen()) {
			try {
				connection.close();
			} catch (Exception e) {
				LOG.error("close connection error:"+ExceptionUtil.getStackTraceAsString(e));
			}
		}
	}
	
}
