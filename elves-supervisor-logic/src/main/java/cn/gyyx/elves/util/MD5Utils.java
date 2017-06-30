package cn.gyyx.elves.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: MD5Utils
 * @Description: MD5 加密字符串
 * @author FuDongFang
 * @date 2016年6月3日 下午2:31:06
 */
public class MD5Utils {

	/**
	 * 将明文参数进行MD5加密
	 * 
	 * @param str
	 *            明文
	 * @return str 密文
	 */
	public static String MD5(String str) {
		if (StringUtils.isEmpty(str) || StringUtils.isBlank(str)) {
			return null;
		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = str.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char strArr[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				strArr[k++] = hexDigits[byte0 >>> 4 & 0xf];
				strArr[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(strArr);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getHashCode(Object object) throws IOException {
		if (object == null) return "";

		String ss = null;
		ObjectOutputStream s = null;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			s = new ObjectOutputStream(bo);
			s.writeObject(object);
			s.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
				s = null;
			}
		}
		ss = MD5(bo.toString());
		return ss;
	}
}
