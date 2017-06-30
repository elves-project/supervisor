package cn.gyyx.elves.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName: SpringUtil
 * @Description: spring 相关工具类
 * @author East.F
 * @date 2016年11月1日 上午11:23:57
 */
public class SpringUtil {

	public static ApplicationContext app=null;
	
	/**
	 * @Title: getBean
	 * @Description: get bean by name
	 * @param beanName
	 * @return Object    返回类型
	 */
	public static Object getBean(String beanName){
		if(null!=app){
			return app.getBean(beanName);
		}
		return null;
	}
	
	/**
	 * @Title: getBean
	 * @Description: get bean by type
	 * @param clz
	 * @throws BeansException 设定文件
	 * @return T    返回类型
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		T result = app.getBean(clz);
		return result;
	}
}
