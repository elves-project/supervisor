package cn.gyyx.elves.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionUtil
 * @Description: 异常处理工具类
 * @author East.F
 * @date 2016年11月7日 上午9:32:57
 */
public class ExceptionUtil {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null){
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
	/**
	 * @Title: stringifyException
	 * @Description: 得到堆栈异常信息字符串
	 * @param e
	 * @return String    返回类型
	 */
	public static String stringifyException(Throwable e) {
		StringWriter stm = new StringWriter();
		PrintWriter pw = new PrintWriter(stm);
		e.printStackTrace(pw);
		pw.close();
		return stm.toString();
	}

}
