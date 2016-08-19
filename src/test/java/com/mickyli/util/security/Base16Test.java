package com.mickyli.util.security;

import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.java.security.Base16Decoder;
import com.mickyli.util.java.security.Base16Encoder;

/** 
 *   <B>说       明</B>:Base16编码解码测试类。
 *
 */
public class Base16Test {

	@Test
	public void testBase16(){
		//建立一个200以内随机长度的随机字节数组。
		SecureRandom random = new SecureRandom();
		int length = random.nextInt(200);
		byte[] data = random.generateSeed(length);
		Base16Encoder encoder = new Base16Encoder();
		byte[] eData = encoder.encode(data);
		Base16Decoder decoder = new Base16Decoder();
		byte[] result = decoder.decode(eData);
		Assert.assertTrue("Base16解码后与编码前数据不相等，"
				+ "编码前数据:"+Arrays.toString(data)+"，解码后数据:"+Arrays.toString(result), Arrays.equals(data, result));
	}
	
}
