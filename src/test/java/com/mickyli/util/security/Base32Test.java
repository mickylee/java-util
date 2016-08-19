package com.mickyli.util.security;

import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.java.security.Base32Decoder;
import com.mickyli.util.java.security.Base32Encoder;


/** 
 *   <B>说       明</B>:Base32编码解码测试类。
 *
 */
public class Base32Test {

	@Test
	public void testBase32(){
		//建立一个300以内随机长度的随机字节数组。
		SecureRandom random = new SecureRandom();
		int length = random.nextInt(300);
		byte[] data = random.generateSeed(length);
		Base32Encoder encoder = new Base32Encoder();
		byte[] eData = encoder.encode(data);
		Base32Decoder decoder = new Base32Decoder();
		byte[] result = decoder.decode(eData);
		Assert.assertTrue("Base32解码后与编码前数据不相等，"
				+ "编码前数据:"+Arrays.toString(data)+"，"
				+ "解码后数据:"+Arrays.toString(result)+"。"
				+ "数据长度为"+length, 
				Arrays.equals(data, result));
	}
	
	@Test
	public void testBase32Hex(){
		//建立一个300以内随机长度的随机字节数组。
		SecureRandom random = new SecureRandom();
		int length = random.nextInt(300);
		byte[] data = random.generateSeed(length);
		Base32Encoder encoder = new Base32Encoder(true);
		byte[] eData = encoder.encode(data);
		Base32Decoder decoder = new Base32Decoder(true);
		byte[] result = decoder.decode(eData);
		Assert.assertTrue("Base32解码后与编码前数据不相等，"
				+ "编码前数据:"+Arrays.toString(data)+"，"
				+ "解码后数据:"+Arrays.toString(result)+"。"
				+ "数据长度为"+length, 
				Arrays.equals(data, result));
	}
	
}
