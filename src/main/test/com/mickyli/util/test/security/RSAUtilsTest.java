package com.mickyli.util.test.security;

import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mickyli.util.j2se.security.KeyPairBean;
import com.mickyli.util.j2se.security.RSAUtils;

/** 
 *   <B>说       明</B>:RSA工具测试类。
 *
 */
public class RSAUtilsTest {
	
	//测试使用的密钥对儿。
	private static KeyPairBean keyPair = null;
	
	@BeforeClass
	public static void init(){
		keyPair = RSAUtils.generateKeyPair();
	}

	@Test
	public void testGenerateKeyPair(){
		int keySize = 512;
		KeyPairBean bean1 = RSAUtils.generateKeyPair(keySize);
		KeyPairBean bean2 = RSAUtils.generateKeyPair(keySize);
		Assert.assertTrue("随机生成的密钥对儿不能为null!", bean1 != null && bean2 != null);
		Assert.assertTrue("两次随机生成的密钥对儿不能相同!", !bean1.equals(bean2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyPairWithIllegalKeySize(){
		SecureRandom random = new SecureRandom();
		boolean randomFlag = random.nextBoolean(); 
		RSAUtils.generateKeyPair(randomFlag ? 123 : 445566);
	}
	
	@Test
	public void testEncryptByPrivateKey(){
		byte[] data = "Nothing Else Matters".getBytes();
		byte[] result = RSAUtils.encryptByPrivateKey(data, keyPair.getPrivateKey(), "RSA/ECB/PKCS1Padding");
		Assert.assertNotNull("加密后的数据不能为null!", result);
		Assert.assertTrue("加密后的数据不能和原始数据相同!", !Arrays.equals(result, data));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptByPrivateKeyWithNullData(){
		byte[] data = null;
		RSAUtils.encryptByPrivateKey(data, keyPair.getPrivateKey(), "RSA/ECB/PKCS1Padding");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptByPrivateKeyWithNullKey(){
		byte[] data = "Nothing Else Matters".getBytes();
		byte[] privateKey = null;
		RSAUtils.encryptByPrivateKey(data, privateKey, "RSA/ECB/PKCS1Padding");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptByPrivateKeyWithIllegalKey(){
		byte[] data = "Nothing Else Matters".getBytes();
		byte[] privateKey = {1,2,3};
		RSAUtils.encryptByPrivateKey(data, privateKey, "RSA/ECB/PKCS1Padding");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptByPrivateKeyWithNullTransformation(){
		byte[] data = "Nothing Else Matters".getBytes();
		String transformation = null;
		RSAUtils.encryptByPrivateKey(data, keyPair.getPrivateKey(), transformation);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEncryptByPrivateKeyWithIllegalTransformation(){
		byte[] data = "Nothing Else Matters".getBytes();
		String transformation = "1/2/3";
		RSAUtils.encryptByPrivateKey(data, keyPair.getPrivateKey(), transformation);
	}
	
	@Test
	public void testDecryptByPublicKey(){
		byte[] data = "Nothing Else Matters".getBytes();
		byte[] result = RSAUtils.encryptByPrivateKey(data, keyPair.getPrivateKey(), "RSA/ECB/PKCS1Padding");
		byte[] dData = RSAUtils.decryptByPublicKey(result, keyPair.getPublicKey(), "RSA/ECB/PKCS1Padding");
		Assert.assertNotNull("解密后的数据不能为null!", dData);
		Assert.assertTrue("解密后的数据和加密之前的数据不一致!\n原始=["+Arrays.toString(data)+"],解密后=["+Arrays.toString(dData)+"]", Arrays.equals(data, dData));
	}
	
	@Test
	public void testEncryptByPubKeyAndDecryptByPriKey(){
		byte[] data = "Nothing Else Matters".getBytes();
		byte[] result = RSAUtils.encryptByPublicKey(data, keyPair.getPublicKey(), "RSA/ECB/PKCS1Padding");
		byte[] dData = RSAUtils.decryptByPrivateKey(result, keyPair.getPrivateKey(), "RSA/ECB/PKCS1Padding");
		Assert.assertNotNull("解密后的数据不能为null!", dData);
		Assert.assertTrue("解密后的数据和加密之前的数据不一致!", Arrays.equals(data, dData));
	}
	
	@Test
	public void testDecryptStringByPublicKey(){
		String dataString = "德国战车 --- Links 2 3 4";
		System.out.println("RSAUtilsTest => testDecryptStringByPublicKey => 私钥加密前的数据:"+dataString);
		String result = RSAUtils.encryptByPrivateKey(dataString, keyPair.getPrivateKey());
		System.out.println("RSAUtilsTest => testDecryptStringByPublicKey => 私钥加密后的数据:"+result);
		String data = RSAUtils.decryptByPublicKey(result, keyPair.getPublicKey());
		System.out.println("RSAUtilsTest => testDecryptStringByPublicKey => 公钥解密后的数据:"+data);
		Assert.assertTrue("解密后的数据和加密之前的数据不一致!", data.equals(dataString));
	}
	
}
