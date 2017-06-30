package cn.gyyx.supervisor.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.gyyx.elves.util.SpringUtil;
import cn.gyyx.elves.util.mq.PropertyLoader;
import cn.gyyx.elves.util.zk.ZookeeperExcutor;

/**
 * @ClassName: SpringContextLoaderListener
 * @Description: spring 监听器，获取ApplicationContext
 * @author East.F
 * @date 2017年6月7日 下午2:36:01
 */
public class SpringContextLoaderListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(SpringContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SpringUtil.app=ctx;
		LOG.info("WebApplicationContext listener start ,ctx :" + ctx);
		
		ZookeeperExcutor zke=new ZookeeperExcutor(PropertyLoader.ZOOKEEPER_HOST,
				PropertyLoader.ZOOKEEPER_OUT_TIME, PropertyLoader.ZOOKEEPER_OUT_TIME);
		String nodeName=zke.createNode(PropertyLoader.ZOOKEEPER_ROOT+"/Supervisor/", "");
		if(null!=nodeName){
			zke.addListener(PropertyLoader.ZOOKEEPER_ROOT+"/Supervisor/", "");
			LOG.info("register zookeeper Supervisor node success");
		}
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}