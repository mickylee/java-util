package com.mickyli.util.security;

import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.java.security.Base64Decoder;
import com.mickyli.util.java.security.Base64Encoder;

/** 
 *   <B>说       明</B>:Base32编码解码测试类。
 *
 */
public class Base64Test {

	@Test
	public void testBase64(){
		//建立一个300以内随机长度的随机字节数组。
		SecureRandom random = new SecureRandom();
		int length = random.nextInt(300);
		byte[] data = random.generateSeed(length);
		Base64Encoder encoder = new Base64Encoder();
		byte[] eData = encoder.encode(data);
		Base64Decoder decoder = new Base64Decoder();
		byte[] result = decoder.decode(eData);
		Assert.assertTrue("Base64解码后与编码前数据不相等，"
				+ "编码前数据:"+Arrays.toString(data)+"，"
				+ "解码后数据:"+Arrays.toString(result)+"。"
				+ "数据长度为"+length, 
				Arrays.equals(data, result));
	}
	
	@Test
	public void testBase64UrlAndFilenameSafe(){
		//建立一个300以内随机长度的随机字节数组。
		SecureRandom random = new SecureRandom();
		int length = random.nextInt(300);
		byte[] data = random.generateSeed(length);
		Base64Encoder encoder = new Base64Encoder(true);
		byte[] eData = encoder.encode(data);
		Base64Decoder decoder = new Base64Decoder();
		byte[] result = decoder.decode(eData);
		Assert.assertTrue("Base64解码后与编码前数据不相等，"
				+ "编码前数据:"+Arrays.toString(data)+"，"
				+ "解码后数据:"+Arrays.toString(result)+"。"
				+ "数据长度为"+length, 
				Arrays.equals(data, result));
	}
	
}
