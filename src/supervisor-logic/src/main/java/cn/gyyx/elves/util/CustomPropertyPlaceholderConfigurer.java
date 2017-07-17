package cn.gyyx.elves.util;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * @ClassName: CustomPropertyPlaceholderConfigurer
 * @Description: 加载property文件并加入到spring中，供spring文件使用
 * 				<p> 业务需求重写PropertyPlaceholderConfigurer，加载自定义属性到spring中去
 * 				   	根据程序启动冲入的配置文件路径 SPRING_CONFIG_PATH 加载该路径下的app.properties文件，
 * 					并将自定义属性 config.path 和 mybaties.path 写入到spring中，供spring文件使用。
 * 				</p>
 * @author FuDongFang
 * @date 2016年6月22日 下午8:08:47
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{

	private static final Logger LOG =Logger.getLogger(CustomPropertyPlaceholderConfigurer.class);

	protected Resource[] locations;
	
	public CustomPropertyPlaceholderConfigurer() {
		super();
		String openapiPath=System.getProperty("SUPERVISOR_PATH");
		LOG.info("get supervisor path :" +openapiPath);
		//初始化，加载  {path}/conf.property 的配置文件
		Resource location=new FileSystemResource(openapiPath+ File.separator+"conf"+File.separator+"conf.properties");
		this.locations = new Resource[]{location};
		super.setLocation(location);
	}
	
	public void addGlobalProperty() {
//        Properties properties = new Properties();
//        properties.setProperty("mybatis.path","");
//        //关键方法,调用的PropertyPlaceholderConfigurer中的方法,通过这个方法将自定义加载的properties文件加入spring中
//        this.setProperties(properties);
    } 
}
