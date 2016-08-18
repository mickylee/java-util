package com.mickyli.util.test.security;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.KeyGenerator;

import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.j2se.security.AESUtils;

/** 
 *   <B>说       明</B>:AES工具测试类。
 *
 */
public class AESUtilsTest {

	private static final Provider provider;

	static{
		try {
			provider = KeyGenerator.getInstance("AES").getProvider();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}

	private static final String[] AES_MODES = provider.get("Cipher.AES SupportedModes").toString().split("\\|");

	private static final String[] AES_PADDINGS = provider.get("Cipher.AES SupportedPaddings").toString().split("\\|");

	@Test
	public void testGenerateKey(){
		byte[] key1 = AESUtils.generateKey();
		byte[] key2 = AESUtils.generateKey();
		Assert.assertNotNull(key1);
		Assert.assertNotNull(key2);
		Assert.assertTrue("key应该包含16个字节!实际为["+key1.length+"]个字节",key1.length == 16 && key2.length == 16);
		Assert.assertFalse("两次随机生成的key不应该相等!",Arrays.equals(key1, key2));
	}

	@Test
	public void testEncrypt(){
		byte[] data = "thismustbe8lengthsoga!!!12345678".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		byte[] result = AESUtils.encrypt(data, key, transformation, iv);
		Assert.assertNotNull("加密后的结果不能为null!", result);
		System.out.println("AESUtilsTest => testEncrypt => encrypt result = "+Arrays.toString(result));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullData(){
		byte[] data = null;
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		AESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullKey(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = null;
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		AESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithKeyLengthNotEqualTo16(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		String transformation = randomCreateTransformation();
		byte[] iv = {8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		AESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullTransformation(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		String transformation = null;
		byte[] iv = {8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		AESUtils.encrypt(data, key, transformation, iv);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithWrongTransformation(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		String transformation = "a/b/cs";
		byte[] iv = {8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8};
		AESUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullIV(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		String transformation = randomCreateTransformation();
		byte[] iv = null;
		AESUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithIVLengthNotEqualTo16(){
		byte[] data = "thismustbe8lengthsoga!!!".getBytes();
		byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
		String transformation = randomCreateTransformation();
		byte[] iv = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,3,4,5,6};
		AESUtils.encrypt(data, key, transformation, iv);
	}
	
	@Test
	public void testEncryptString(){
		String input = "密码学中的高级加密标准（Advanced Encryption Standard，AES），又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。";
		String key = "MyKey!";
		String result = AESUtils.encrypt(input, key);
		Assert.assertNotNull("加密结果不能为null!", result);
		System.out.println("AESUtilsTest => testEncryptString => AES加密后的结果 ="+result);
	}
	
	@Test
	public void testDecryptString(){
		String input = "密码学中的高级加密标准（Advanced Encryption Standard，AES），又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。";
		String key = "MyKey!";
		String result = AESUtils.encrypt(input, key);
		String data = AESUtils.decrypt(result, key);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDecryptStringWithWrongKey(){
		String input = "密码学中的高级加密标准（Advanced Encryption Standard，AES），又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。";
		String key = "MyKey!";
		String wrongKey = "HolyShit!";
		String result = AESUtils.encrypt(input, key);
		String data = AESUtils.decrypt(result, wrongKey);
		Assert.assertEquals("原始数据为["+input+"],解密后的数据为["+data+"].", input, data);
	}

	/**
	 * 随机创建一个转换名称。
	 * @return
	 */
	public static String randomCreateTransformation(){
		Random random = new SecureRandom();
		int modeIndex = random.nextInt(AES_MODES.length);
		int paddingIndex = random.nextInt(AES_PADDINGS.length);
		StringBuilder builder = new StringBuilder();
		builder.append("AES");
		builder.append("/");
		String mode = AES_MODES[modeIndex];
		builder.append(mode);
		builder.append("/");
		String padding = AES_PADDINGS[paddingIndex];
		if(mode.equals("CTR") || mode.equals("CTS")){
			padding = "NoPadding";
		}
		builder.append(padding);
		return builder.toString();
	}
	
	public static void main(String[] args) {
		for(String mode : AES_MODES){
			for(String p : AES_PADDINGS){
				String info = "AES/"+mode+"/"+p;
				System.out.println(info);
				byte[] data = "11111111222222223333333344444444".getBytes();
				byte[] key = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6};
				try{
				AESUtils.encrypt(data, key, info);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
}
