package com.ando.exam.utils;

import org.springframework.util.DigestUtils;

public class MD5Utils {
	/**
	 * MD5方法
	 * 
	 * @param text 明文
	 * @return 密文
	 * @throws Exception
	 */
	public static String md5(String text) throws Exception {
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}

	/**
	 * MD5验证方法
	 * 
	 * @param text 明文
	 * @param md5  密文
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(String text, String md5) throws Exception {
		String md5Text = md5(text);
		if (md5Text.equalsIgnoreCase(md5)) {
			return true;
		}
		return false;
	}
}
