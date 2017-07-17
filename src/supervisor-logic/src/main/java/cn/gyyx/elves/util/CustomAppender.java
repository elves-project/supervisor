package cn.gyyx.elves.util;

import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;

/**
 * @ClassName: CustomAppender
 * @Description: 自定义log4 jappender,可以根据不通的level等级，区分日志
 * @author East.F
 * @date 2016年6月3日 上午10:46:37
 */
public class CustomAppender extends RollingFileAppender {  
    
  @Override  
  public boolean isAsSevereAsThreshold(Priority priority) { 
	  //只判断是否相等，而不判断优先级
      return this.getThreshold().equals(priority);    
  }    
}
