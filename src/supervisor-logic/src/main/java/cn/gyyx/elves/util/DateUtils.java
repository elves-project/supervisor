/*---------------------------------------------------
 * 版权所有：北京光宇华夏科技有限责任公司
 * 作者：bjkandy
 * 联系方式：kangruiwei@gyyx.cn
 * 创建时间：2015年3月13日 上午10:51:28
 * 版本号：v1.0
 *
 * 注意：本内容仅限于公司内部使用，禁止转发。
 * ------------------------------------------------*/
package cn.gyyx.elves.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DateUtils
 * @Description: 日期工具类
 * @author East.F
 * @date 2015年3月13日 上午10:51:28
 * 
 */
public class DateUtils {
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static String currentTimestamp2String(String format){
		if(StringUtils.isEmpty(format)){
			format = DateUtils.DEFAULT_DATETIME_FORMAT;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	public static Date String2Date(String sourceTime) {
		return string2Date(sourceTime,DateUtils.DEFAULT_DATETIME_FORMAT);
	}
	
	public static Date string2Date(String sourceTime,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(sourceTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String date2String(Date date){
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.format(date);
		} else {
			return null;
		}
	}
	
	public static String date2String(Date date,String format){
		if (null == format || StringUtils.isEmpty(format)) {
			format = DateUtils.DEFAULT_DATETIME_FORMAT;
		}

		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return null;
		}
	}

	/**
	 * 获得前几天的时间
	 * 
	 * @param now
	 * @param day
	 * @return
	 */
	public static String getDateBefore(String now, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.String2Date(now));
		c.set(Calendar.DATE, c.get(Calendar.DATE) - day);
		return DateUtils.date2String(c.getTime());
	}

	/**
	 * 获得后几天的时间
	 * 
	 * @param now
	 * @param day
	 * @return
	 */
	public static String getDateAfter(String now, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.String2Date(now));
		c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
		return DateUtils.date2String(c.getTime());
	}
}
