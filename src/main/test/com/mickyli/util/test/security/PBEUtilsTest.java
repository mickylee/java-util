package com.mickyli.util.test.security;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.j2se.security.PBEUtils;
import com.mickyli.util.j2se.security.PBEUtils.PBEAlgorithm;

/** 
 *   <B>说       明</B>:PBEUtils工具测试类
 */
public class PBEUtilsTest {

	@Test
	public void testGenerateSalt(){
		byte[] salt1 = PBEUtils.generateSalt();
		byte[] salt2 = PBEUtils.generateSalt();
		Assert.assertNotNull(salt1);
		Assert.assertNotNull(salt2);
		Assert.assertTrue("salt应该包含8个字节!",salt1.length == 8 && salt2.length == 8);
		Assert.assertFalse("两次随机生成的salt不应该相等!",Arrays.equals(salt1, salt2));
	}

	@Test
	public void testEncrypt(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		String password = "123456";
		byte[] salt = {1,2,3,4,5,6,7,8};
		byte[] result = PBEUtils.encrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
		Assert.assertNotNull("加密后的结果不能为null!", result);
		System.out.println("PBEUtilsTest => testEncrypt => encrypt result = "+Arrays.toString(result));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullData(){
		byte[] data = null;
		String password = "123456";
		byte[] salt = {1,2,3,4,5,6,7,8};
		PBEUtils.encrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullPassword(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		String password = null;
		byte[] salt = {1,2,3,4,5,6,7,8};
		PBEUtils.encrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullSalt(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		String password = "123456";
		byte[] salt = null;
		PBEUtils.encrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithSaltLengthNotEqualTo8(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		String password = "123456";
		byte[] salt = {1,2};
		PBEUtils.encrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullPBEAlgorithm(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		String password = "123456";
		byte[] salt = {1,2,3,4,5,6,7,8};
		PBEUtils.encrypt(data, password, salt, null);
	}
	
	@Test
	public void testEncryptString(){
		String input = "虚拟化办公环境给工作带来了各种不方便，难道这个产品的目的是为了让某些企业里闲的蛋疼的人忙起来？这个太牛逼了。";
		String password = "123456!";
		String result = PBEUtils.encrypt(input, password);
		Assert.assertNotNull("加密结果不能为null!", result);
		System.out.println("PBEUtilsTest => testEncryptString => PBE加密后的结果 ="+result);
	}
	
	@Test
	public void testDecryptString(){
		String input = "虚拟化办公环境给工作带来了各种不方便，难道这个产品的目的是为了让某些企业里闲的蛋疼的人忙起来？这个太牛逼了。";
		String password = "123456!";
		String result = PBEUtils.encrypt(input, password);
		String data = PBEUtils.decrypt(result, password);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDecryptStringWithWrongPassword(){
		String input = "虚拟化办公环境给工作带来了各种不方便，难道这个产品的目的是为了让某些企业里闲的蛋疼的人忙起来？这个太牛逼了。";
		String password = "123456!";
		String wrongPassword = "HolyShit!";
		String result = PBEUtils.encrypt(input, password);
		String data = PBEUtils.decrypt(result, wrongPassword);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}
	
}
