package com.mickyli.util.test.security; 

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.j2se.security.MessageDigestUtils;

/** 
 *   <B>说       明</B>:信息摘要工具测试类
 *   
 */
public class MessageDigestUtilsTest {

	@Test
	public void testGetMD2Digest(){
		String msg = "ChildrenOfBodom";
		Assert.assertArrayEquals(MessageDigestUtils.getMD2Digest(msg.getBytes()),
				MessageDigestUtils.getMD2Digest(msg.getBytes()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMD2DigestWithNull(){
		byte[] input = null;
		MessageDigestUtils.getMD2Digest(input);
	}

	@Test
	public void testGetMD2DigestFromInputStream(){
		try {
			String msg = "ChildrenOfBodom";
			ByteArrayInputStream bis1 = new ByteArrayInputStream(msg.getBytes());
			ByteArrayInputStream bis2 = new ByteArrayInputStream(msg.getBytes());
			Assert.assertArrayEquals(MessageDigestUtils.getMD2Digest(bis1),
					MessageDigestUtils.getMD2Digest(bis2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMD2DigestFromInputStreamWithNull(){
		InputStream input = null;
		try {
			MessageDigestUtils.getMD2Digest(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMD5Digest(){
		String msg = "Guns n' Roses";
		Assert.assertArrayEquals(MessageDigestUtils.getMD5Digest(msg.getBytes()),
				MessageDigestUtils.getMD5Digest(msg.getBytes()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMD5DigestWithNull(){
		byte[] input = null;
		MessageDigestUtils.getMD5Digest(input);
	}

	@Test
	public void testGetMD5DigestFromInputStream(){
		try{
			String msg = "Guns n' Roses";
			ByteArrayInputStream bis1 = new ByteArrayInputStream(msg.getBytes());
			ByteArrayInputStream bis2 = new ByteArrayInputStream(msg.getBytes());
			Assert.assertArrayEquals(MessageDigestUtils.getMD5Digest(bis1),
					MessageDigestUtils.getMD5Digest(bis2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMD5DigestFromInputStreamWithNull(){
		InputStream input = null;
		try {
			MessageDigestUtils.getMD5Digest(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSHADigest(){
		String msg = "Metallica";
		Assert.assertArrayEquals(MessageDigestUtils.getSHADigest(msg.getBytes()),
				MessageDigestUtils.getSHADigest(msg.getBytes()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHADigestWithNull(){
		byte[] input = null;
		MessageDigestUtils.getSHADigest(input);
	}

	@Test
	public void testGetSHADigestFromInputStream(){
		try{
			String msg = "Metallica";
			ByteArrayInputStream bis1 = new ByteArrayInputStream(msg.getBytes());
			ByteArrayInputStream bis2 = new ByteArrayInputStream(msg.getBytes());
			Assert.assertArrayEquals(MessageDigestUtils.getSHADigest(bis1),
					MessageDigestUtils.getSHADigest(bis2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHADigestFromInputStreamWithNull(){
		InputStream input = null;
		try {
			MessageDigestUtils.getSHADigest(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSHA256Digest(){
		String msg = "Green Day";
		Assert.assertArrayEquals(MessageDigestUtils.getSHA256Digest(msg.getBytes()),
				MessageDigestUtils.getSHA256Digest(msg.getBytes()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHA256DigestWithNull(){
		byte[] input = null;
		MessageDigestUtils.getSHA256Digest(input);
	}

	@Test
	public void testGetSHA256DigestFromInputStream(){
		try{
			String msg = "Green Day";
			ByteArrayInputStream bis1 = new ByteArrayInputStream(msg.getBytes());
			ByteArrayInputStream bis2 = new ByteArrayInputStream(msg.getBytes());
			Assert.assertArrayEquals(MessageDigestUtils.getSHA256Digest(bis1),
					MessageDigestUtils.getSHA256Digest(bis2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHA256DigestFromInputStreamWithNull(){
		InputStream input = null;
		try {
			MessageDigestUtils.getSHA256Digest(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSHA384Digest(){
		String msg = "Skid Row";
		Assert.assertArrayEquals(MessageDigestUtils.getSHA384Digest(msg.getBytes()),
				MessageDigestUtils.getSHA384Digest(msg.getBytes()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHA384DigestWithNull(){
		byte[] input = null;
		MessageDigestUtils.getSHA384Digest(input);
	}

	@Test
	public void testGetSHA384DigestFromInputStream(){
		try{
			String msg = "Skid Row";
			ByteArrayInputStream bis1 = new ByteArrayInputStream(msg.getBytes());
			ByteArrayInputStream bis2 = new ByteArrayInputStream(msg.getBytes());
			Assert.assertArrayEquals(MessageDigestUtils.getSHA384Digest(bis1),
					MessageDigestUtils.getSHA384Digest(bis2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHA384DigestFromInputStreamWithNull(){
		InputStream input = null;
		try {
			MessageDigestUtils.getSHA384Digest(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSHA512Digest(){
		String msg = "Rammstein";
		Assert.assertArrayEquals(MessageDigestUtils.getSHA512Digest(msg.getBytes()),
				MessageDigestUtils.getSHA512Digest(msg.getBytes()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHA512DigestWithNull(){
		byte[] input = null;
		MessageDigestUtils.getSHA512Digest(input);
	}

	@Test
	public void testGetSHA512DigestFromInputStream(){
		try{
			String msg = "Rammstein";
			ByteArrayInputStream bis1 = new ByteArrayInputStream(msg.getBytes());
			ByteArrayInputStream bis2 = new ByteArrayInputStream(msg.getBytes());
			Assert.assertArrayEquals(MessageDigestUtils.getSHA512Digest(bis1),
					MessageDigestUtils.getSHA512Digest(bis2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSHA512DigestFromInputStreamWithNull(){
		InputStream input = null;
		try {
			MessageDigestUtils.getSHA512Digest(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
