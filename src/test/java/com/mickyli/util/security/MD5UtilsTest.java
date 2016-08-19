package com.mickyli.util.security; 

import org.junit.Test;

import com.mickyli.util.java.security.MD5Utils;


/** 
 *   <B>说       明</B>:MD5工具测试类
 *   
 */
public class MD5UtilsTest {

	@Test
	public void testGetMD5(){
		String input = "fadetoblack3344";
		String result = MD5Utils.getMD5(input);
		System.out.println("MD5UtilsTest => testGetMD5=> result:"+result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetMD5WithNull(){
		String input = null;
		MD5Utils.getMD5(input);
	}
	
	
	
}
 