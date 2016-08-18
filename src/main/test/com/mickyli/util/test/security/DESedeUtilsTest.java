package com.mickyli.util.test.security;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.KeyGenerator;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.j2se.security.DESedeUtils;

/** 
 *   <B>说       明</B>:DESede工具测试类。
 *
 */
public class DESedeUtilsTest {

	private static final Provider provider;

	static{
		try {
			provider = KeyGenerator.getInstance("DESede").getProvider();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}

	private static final String[] DESEDE_MODES = provider.get("Cipher.DESede SupportedModes").toString().split("\\|");

	private static final String[] DESEDE_PADDINGS = provider.get("Cipher.DESede SupportedPaddings").toString().split("\\|");

	@Test
	public void testGenerateKey(){
		byte[] key1 = DESedeUtils.generateKey();
		byte[] key2 = DESedeUtils.generateKey();
		Assert.assertNotNull(key1);
		Assert.assertNotNull(key2);
		Assert.assertTrue("key应该包含24个字节!",key1.length == 24 && key2.length == 24);
		Assert.assertFalse("两次随机生成的key不应该相等!",Arrays.equals(key1, key2));
	}

	@Test
	public void testEncrypt(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		byte[] result = DESedeUtils.encrypt(data, key, transformation, iv);
		Assert.assertNotNull("加密后的结果不能为null!", result);
		System.out.println("DESedeUtilsTest => testEncrypt => encrypt result = "+Arrays.toString(result));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullData(){
		byte[] data = null;
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESedeUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullKey(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = null;
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESedeUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithKeyLengthLightThan24(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESedeUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullTransformation(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
		String transformation = null;
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESedeUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithWrongTransformation(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
		String transformation = "a/b/cs";
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESedeUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullIV(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
		String transformation = randomCreateTransformation();
		byte[] iv = null;
		DESedeUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithIVLengthNotEqualTo8(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
		String transformation = randomCreateTransformation();
		byte[] iv = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		DESedeUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test
	public void testEncryptString(){
		String input = "DESede是由DES对称加密算法改进后的一种对称加密算法。使用 168 位的密钥对资料进行三次加密的一种机制。";
		String key = "MyKey!";
		String result = DESedeUtils.encrypt(input, key);
		Assert.assertNotNull("加密结果不能为null!", result);
		System.out.println("DESedeUtilsTest => testEncryptString => DESede加密后的结果 ="+result);
	}
	
	@Test
	public void testDecryptString(){
		String input = "DESede是由DES对称加密算法改进后的一种对称加密算法。使用 168 位的密钥对资料进行三次加密的一种机制。";
		String key = "MyKey!";
		String result = DESedeUtils.encrypt(input, key);
		String data = DESedeUtils.decrypt(result, key);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDecryptStringWithWrongKey(){
		String input = "DESede是由DES对称加密算法改进后的一种对称加密算法。使用 168 位的密钥对资料进行三次加密的一种机制。";
		String key = "MyKey!";
		String wrongKey = "HolyShit!";
		String result = DESedeUtils.encrypt(input, key);
		String data = DESedeUtils.decrypt(result, wrongKey);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}

	/**
	 * 随机创建一个转换名称。
	 * @return
	 */
	public static String randomCreateTransformation(){
		Random random = new SecureRandom();
		int modeIndex = random.nextInt(DESEDE_MODES.length);
		int paddingIndex = random.nextInt(DESEDE_PADDINGS.length);
		StringBuilder builder = new StringBuilder();
		builder.append("DESede");
		builder.append("/");
		String mode = DESEDE_MODES[modeIndex];
		builder.append(mode);
		builder.append("/");
		String padding = DESEDE_PADDINGS[paddingIndex];
		if(mode.equals("CTR") || mode.equals("CTS")){
			padding = "NoPadding";
		}
		builder.append(padding);
		return builder.toString();
	}

}
