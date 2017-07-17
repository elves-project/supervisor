package cn.gyyx.supervisor.listener;

import cn.gyyx.elves.util.SpringUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by eastFu on 2017/7/17 0017.
 */
public class WebContextListener extends ContextLoaderListener {

    private final  static org.slf4j.Logger LOG= LoggerFactory.getLogger(WebContextListener.class);
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {

        String openapiPath =System.getProperty("SUPERVISOR_PATH");

        SpringUtil.PROPERTIES_CONFIG_PATH=openapiPath+ File.separator+"conf"+File.separator+"conf.properties";

        PropertyConfigurator.configure(openapiPath+File.separator+"conf"+File.separator+"log4j.properties");

        LOG.info("conf.properties path :"+SpringUtil.PROPERTIES_CONFIG_PATH);

        return super.initWebApplicationContext(servletContext);
    }
}
