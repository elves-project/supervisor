package cn.gyyx.elves.util.mq;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.gyyx.elves.util.SpringUtil;
import org.apache.log4j.Logger;

import cn.gyyx.elves.util.ExceptionUtil;

/**
 * @ClassName: PropertyLoader
 * @Description: *.property 配置文件加载类
 * @author East.F
 * @date 2016年5月5日 上午11:51:27
 */
public class PropertyLoader {

	private static final Logger LOG=Logger.getLogger(PropertyLoader.class);
	
	// 属性列表
	private static Properties properties = new Properties();
	
	// 读取资源文件,设置输入流
	private static InputStream is = null;

	//zookeeper.enabled
	public static String ZOOKEEPER_ENABLED;

	//zk host
	public static String ZOOKEEPER_HOST;
	
	//zk out-time
	public static int ZOOKEEPER_OUT_TIME;
	
    public static String ZOOKEEPER_ROOT;
	
	//mq host
	public static String MQ_IP;
	
	//mq port
	public static int MQ_PORT;
	
	//mq user
	public static String MQ_USER;
	
	//mq pwd
	public static String MQ_PASSWORD;
	
	//rpc exchange name
	public static String MQ_EXCHANGE;
	
	public static String FTP_RES_IP;
	
	public static String FTP_RES_USER;
	
	public static String FTP_RES_PASS;
	
	static {
		try {
			is = is = is = new FileInputStream(SpringUtil.PROPERTIES_CONFIG_PATH);;
			properties.load(is);

			ZOOKEEPER_ENABLED=properties.getProperty("zookeeper.enabled");
			ZOOKEEPER_HOST = properties.getProperty("zookeeper.host");
    		ZOOKEEPER_OUT_TIME =Integer.parseInt(properties.getProperty("zookeeper.outTime"));
    		ZOOKEEPER_ROOT=properties.getProperty("zookeeper.root");
    		
    		MQ_IP = properties.getProperty("mq.ip");
    		MQ_PORT = Integer.parseInt(properties.getProperty("mq.port"));
    		MQ_USER = properties.getProperty("mq.user");
    		MQ_PASSWORD = properties.getProperty("mq.password");
    		MQ_EXCHANGE = properties.getProperty("mq.exchange");
    		
    		FTP_RES_IP = properties.getProperty("ftp.res.ip");
    		FTP_RES_USER = properties.getProperty("ftp.res.user");
    		FTP_RES_PASS = properties.getProperty("ftp.res.pass");
    		
		} catch (IOException e) {
			LOG.error(ExceptionUtil.getStackTraceAsString(e));
		}
	}
}
