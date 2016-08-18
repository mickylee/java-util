package com.mickyli.util.test.security; 

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.j2se.security.Base64Utils;


/** 
 *   <B>说       明</B>:标准Base64工具测试类
 *   
 */
public class Base64UtilsTest {

	@Test
	public void testEncode(){
		byte[] data = {3,4,5,6,8,11,2,4,122,8,5,3,2};
		byte[] result = Base64Utils.encode(data);
		Assert.assertNotNull(result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncodeWithNullData(){
		byte[] data = null;
		Base64Utils.encode(data);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncodeWithNullCharset(){
		String data = "12345"; 
		String charset = null;
		Base64Utils.encode(data,charset);
	}
	
	@Test
	public void testEncode2string(){
		byte[] data = {3,4,5,6,8,11,2,4,122,8,5,3,2};
		String result = Base64Utils.encode2string(data);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDecode(){
		String data = "算法导论 ！！@@%%………… MIT ***** !";
		System.out.println("Base64UtilsTest => testDecode => Base64编码前:"+data);
		String eData = Base64Utils.encode2string(data, "gb2312");
		System.out.println("Base64UtilsTest => testDecode => Base64编码后:"+eData);
		String result = Base64Utils.decode2string(eData,"gb2312");
		System.out.println("Base64UtilsTest => testDecode => Base64解码后:"+result);
		Assert.assertEquals("Base64解码后数据与编码前数据不一致。编码前数据："+data+"，解码后数据："+result, data, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithNullData(){
		byte[] data = null;
		Base64Utils.decode(data);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDecode2stringWithNullCharset(){
		byte[] data = {3,4,5,6,8,11,2,4,122,8,5,3,2};
		String charset = null;
		Base64Utils.decode2string(data, charset);
	}
	
	
}
 