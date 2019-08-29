package com.alibaba.otter.manager.biz.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Random;

/**
 * 加密工具类
 * @author pm
 * @version 创建时间：2014年11月24日  下午3:44:04
 */
public class EncryptUtil {
	
	public static String signHmacSHA1(String key, String data) throws Exception {
		byte signData[] = signHmacSHA1(key.getBytes("UTF-8"), data.getBytes("UTF-8"));
		return toBase64String(signData);
	}

	public static byte[] signHmacSHA1(byte key[], byte data[]) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(new SecretKeySpec(key, "HmacSHA1"));
		return mac.doFinal(data);
	}

	public static String toBase64String(byte binaryData[]) {
		return Base64.getEncoder().encodeToString(binaryData);
	}

	public static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
