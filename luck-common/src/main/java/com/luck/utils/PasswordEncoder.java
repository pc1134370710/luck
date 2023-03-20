package com.luck.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
* @ClassName:       PasswordEncoder
*                   密码加密
*/
public class PasswordEncoder {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private final static String MD5 = "MD5";
	private final static String SHA = "SHA";


	/**
	 * 密码加密
	 * @param rawPass
	 * @param salt
	 * @return
	 */
	public static String encode(String rawPass,String salt) {
		String result = null;
		try {
			rawPass = rawPass+salt;
			MessageDigest md = MessageDigest.getInstance(MD5);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(rawPass.getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}


	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将字节转换为16进制
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}


    public static String getSalt() {
		String s = UUID.randomUUID().toString();
		return s.replace("-","");
	}
}