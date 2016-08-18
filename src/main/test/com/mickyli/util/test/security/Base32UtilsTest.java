package com.mickyli.util.test.security; 

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.j2se.security.Base32Utils;

/** 
 *   <B>说       明</B>:标准Base32工具测试类
 */
public class Base32UtilsTest {

	@Test
	public void testEncode(){
		byte[] data = {3,4,5,6,8,11,2,4,122,8,5,3,2};
		byte[] result = Base32Utils.encode(data);
		Assert.assertNotNull(result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncodeWithNullData(){
		byte[] data = null;
		Base32Utils.encode(data);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncodeWithNullCharset(){
		String data = "12345"; 
		String charset = null;
		Base32Utils.encode(data,charset);
	}
	
	@Test
	public void testEncode2string(){
		byte[] data = {3,4,5,6,8,11,2,4,122,8,5,3,2};
		String result = Base32Utils.encode2string(data);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDecode(){
		String data = "计算机程序设计艺术 ---- Knuth";
		System.out.println("Base32UtilsTest => testDecode => Base32编码前:"+data);
		String eData = Base32Utils.encode2string(data, "gb2312");
		System.out.println("Base32UtilsTest => testDecode => Base32编码后:"+eData);
		String result = Base32Utils.decode2string(eData,"gb2312");
		System.out.println("Base32UtilsTest => testDecode => Base32解码后:"+result);
		Assert.assertEquals("Base32解码后数据与编码前数据不一致。编码前数据："+data+"，解码后数据："+result, data, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithNullData(){
		byte[] data = null;
		Base32Utils.decode(data);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDecode2stringWithNullCharset(){
		byte[] data = {3,4,5,6,8,11,2,4,122,8,5,3,2};
		String charset = null;
		Base32Utils.decode2string(data, charset);
	}
	
	
}
 