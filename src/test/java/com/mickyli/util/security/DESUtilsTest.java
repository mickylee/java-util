package com.mickyli.util.security;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import javax.crypto.KeyGenerator;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.java.security.DESUtils;

/** 
 *   <B>说       明</B>:DES工具测试类。
 *
 */
public class DESUtilsTest {

	private static final Provider provider;

	static{
		try {
			provider = KeyGenerator.getInstance("DES").getProvider();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}

	private static final String[] DES_MODES = provider.get("Cipher.DES SupportedModes").toString().split("\\|");

	private static final String[] DES_PADDINGS = provider.get("Cipher.DES SupportedPaddings").toString().split("\\|");

	@Test
	public void testGenerateKey(){
		byte[] key1 = DESUtils.generateKey();
		byte[] key2 = DESUtils.generateKey();
		Assert.assertNotNull(key1);
		Assert.assertNotNull(key2);
		Assert.assertTrue("key应该包含8个字节!",key1.length == 8 && key2.length == 8);
		Assert.assertFalse("两次随机生成的key不应该相等!",Arrays.equals(key1, key2));
	}

	@Test
	public void testEncrypt(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		byte[] result = DESUtils.encrypt(data, key, transformation, iv);
		Assert.assertNotNull("加密后的结果不能为null!", result);
		System.out.println("DESUtilsTest => testEncrypt => encrypt result = "+Arrays.toString(result));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullData(){
		byte[] data = null;
		byte[] key = {1,2,3,4,5,6,7,8};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullKey(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = null;
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithKeyLengthLightThan8(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullTransformation(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8};
		String transformation = null;
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithWrongTransformation(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8};
		String transformation = "a/b/cs";
		byte[] iv = {8,7,6,5,4,3,2,1};
		DESUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullIV(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8};
		String transformation = randomCreateTransformation();
		byte[] iv = null;
		DESUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithIVLengthNotEqualTo8(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8};
		String transformation = randomCreateTransformation();
		byte[] iv = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		DESUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test
	public void testEncryptString(){
		String input = "数据加密算法(Data Encryption Algorithm DEA)是一种对称加密算法，很可能是使用最广泛的密钥系统，特别是在保护金融数据的安全中，最初开发的DEA是嵌入硬件中的";
		String key = "MyKey!";
		String result = DESUtils.encrypt(input, key);
		Assert.assertNotNull("加密结果不能为null!", result);
		System.out.println("DESUtilsTest => testEncryptString => DES加密后的结果 ="+result);
	}
	
	@Test
	public void testDecryptString(){
		String input = "数据加密算法(Data Encryption Algorithm DEA)是一种对称加密算法，很可能是使用最广泛的密钥系统，特别是在保护金融数据的安全中，最初开发的DEA是嵌入硬件中的";
		String key = "MyKey!";
		String result = DESUtils.encrypt(input, key);
		String data = DESUtils.decrypt(result, key);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDecryptStringWithWrongKey(){
		String input = "数据加密算法(Data Encryption Algorithm DEA)是一种对称加密算法，很可能是使用最广泛的密钥系统，特别是在保护金融数据的安全中，最初开发的DEA是嵌入硬件中的";
		String key = "MyKey!";
		String wrongKey = "HolyShit!";
		String result = DESUtils.encrypt(input, key);
		String data = DESUtils.decrypt(result, wrongKey);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}

	/**
	 * 随机创建一个转换名称。
	 * @return
	 */
	public static String randomCreateTransformation(){
		Random random = new SecureRandom();
		int modeIndex = random.nextInt(DES_MODES.length);
		int paddingIndex = random.nextInt(DES_PADDINGS.length);
		StringBuilder builder = new StringBuilder();
		builder.append("DES");
		builder.append("/");
		String mode = DES_MODES[modeIndex];
		builder.append(mode);
		builder.append("/");
		String padding = DES_PADDINGS[paddingIndex];
		if(mode.equals("CTR") || mode.equals("CTS")){
			padding = "NoPadding";
		}
		builder.append(padding);
		return builder.toString();
	}
	
	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		String es = DESUtils.encrypt("abdc", uuid.substring(1,3));
		System.out.println(es);
		String ds = DESUtils.decrypt(es, uuid.substring(1,3));
		System.out.println(ds);
	}

}
