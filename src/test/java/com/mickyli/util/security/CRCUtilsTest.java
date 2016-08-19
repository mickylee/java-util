package com.mickyli.util.security; 

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.java.security.CRCUtils;

/** 
 *   <B>说       明</B>:循环冗余校验工具测试类
 *   
 */
public class CRCUtilsTest {

	@Test
	public void testGetCRC32Value(){
		byte[] input = new byte[]{45,89,-87,45,112};
		long crc32 = CRCUtils.getCRC32Value(input);
		Assert.assertTrue(crc32 > 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetCRC32ValueWithNull(){
		byte[] input = null;
		CRCUtils.getCRC32Value(input);
	}
	
	@Test
	public void testGetCRC32ValueFromInputStream(){
		try {
			byte[] input = new byte[]{45,89,-87,45,112};
			ByteArrayInputStream bis = new ByteArrayInputStream(input);
			long crc32 = CRCUtils.getCRC32Value(bis);
			Assert.assertTrue(crc32 > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetCRC32ValueFromInputStreamWithNull(){
		InputStream input = null;
		try {
			CRCUtils.getCRC32Value(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCRC32StringValue(){
		byte[] input = new byte[]{45,89,-87,45,112};
		String crc32 = CRCUtils.getCRC32StringValue(input);
		System.out.println("CRCUtilsTest => testGetCRC32StringValue => CRC32:"+crc32);
		Assert.assertNotNull(crc32);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetCRC32StringValueWithNull(){
		byte[] input = null;
		CRCUtils.getCRC32StringValue(input);
	}
	
	@Test
	public void testGetCRC32StringValueFromInputStream(){
		try {
			byte[] input = new byte[]{45,89,-87,45,112};
			ByteArrayInputStream bis = new ByteArrayInputStream(input);
			String crc32 = CRCUtils.getCRC32StringValue(bis);
			System.out.println("CRCUtilsTest => testGetCRC32StringValueFromInputStream => CRC32:"+crc32);
			Assert.assertNotNull(crc32);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetCRC32StringValueFromInputStreamWithNull(){
		InputStream input = null;
		try {
			CRCUtils.getCRC32StringValue(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAdler32Value(){
		byte[] input = new byte[]{45,89,-87,45,112};
		long crc32 = CRCUtils.getAdler32Value(input);
		Assert.assertTrue(crc32 > 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetAdler32ValueWithNull(){
		byte[] input = null;
		CRCUtils.getAdler32Value(input);
	}
	
	@Test
	public void testGetAdler32ValueFromInputStream(){
		try {
			byte[] input = new byte[]{45,89,-87,45,112};
			ByteArrayInputStream bis = new ByteArrayInputStream(input);
			long crc32 = CRCUtils.getAdler32Value(bis);
			Assert.assertTrue(crc32 > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetAdler32ValueFromInputStreamWithNull(){
		InputStream input = null;
		try {
			CRCUtils.getAdler32Value(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAdler32StringValue(){
		byte[] input = new byte[]{45,89,-87,45,112};
		String crc32 = CRCUtils.getAdler32StringValue(input);
		System.out.println("CRCUtilsTest => testGetAdler32StringValue => Adler32:"+crc32);
		Assert.assertNotNull(crc32);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetAdler32StringValueWithNull(){
		byte[] input = null;
		CRCUtils.getAdler32StringValue(input);
	}
	
	@Test
	public void testGetAdler32StringValueFromInputStream(){
		try {
			byte[] input = new byte[]{45,89,-87,45,112};
			ByteArrayInputStream bis = new ByteArrayInputStream(input);
			String crc32 = CRCUtils.getAdler32StringValue(bis);
			System.out.println("CRCUtilsTest => testGetAdler32StringValueFromInputStream => Adler32:"+crc32);
			Assert.assertNotNull(crc32);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetAdler32StringValueFromInputStreamWithNull(){
		InputStream input = null;
		try {
			CRCUtils.getAdler32StringValue(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
 