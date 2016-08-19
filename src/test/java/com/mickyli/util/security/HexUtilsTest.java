package com.mickyli.util.security; 

import org.junit.Test;

import com.mickyli.util.java.security.HexUtils;


/** 
 *   <B>说       明</B>:十六进制工具类
 *   
 */
public class HexUtilsTest {

	@Test
	public void testToHexString(){
		byte[] data = new byte[]{56,78,-123,44,5,78};
		String result = HexUtils.toHexString(data);
		System.out.println("HexUtilsTest => testToHexString => result(HexString):"+result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testToHexStringWithNull(){
		byte[] data = null;
		HexUtils.toHexString(data);
	}
	
}
 