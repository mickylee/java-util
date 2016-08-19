package com.mickyli.util.security;

import java.security.SecureRandom;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mickyli.util.java.security.DSASignatureUtils;
import com.mickyli.util.java.security.KeyPairBean;
import com.mickyli.util.java.security.DSASignatureUtils.SignatureAlgorithm;


/** 
 *   <B>说       明</B>:DSA数字签名工具测试类。
 *
 */
public class DSASignatureUtilsTest {

	//测试使用的密钥对儿。
	private static KeyPairBean keyPair = null;

	@BeforeClass
	public static void init(){
		keyPair = DSASignatureUtils.generateKeyPair();
	}
	
	@Test
	public void testGenerateKeyPair(){
		int keySize = 512;
		KeyPairBean bean1 = DSASignatureUtils.generateKeyPair(keySize);
		KeyPairBean bean2 = DSASignatureUtils.generateKeyPair(keySize);
		Assert.assertTrue("随机生成的密钥对儿不能为null!", bean1 != null && bean2 != null);
		Assert.assertTrue("两次随机生成的密钥对儿不能相同!", !bean1.equals(bean2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyPairWithIllegalKeySize(){
		SecureRandom random = new SecureRandom();
		boolean randomFlag = random.nextBoolean(); 
		DSASignatureUtils.generateKeyPair(randomFlag ? 123 : 1025);
	}

	@Test
	public void testSign(){
		byte[] data = "Wake me up when September ends.".getBytes();
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), SignatureAlgorithm.SHA1WithDSA);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignWithNullData(){
		byte[] data = null;
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), SignatureAlgorithm.SHA1WithDSA);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignWithNullPrivateKey(){
		byte[] data = "Wake me up when September ends.".getBytes();
		byte[] privateKey = null;
		byte[] signature = DSASignatureUtils.sign(data, privateKey, SignatureAlgorithm.SHA1WithDSA);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignWithNullSignatureAlgorithm(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = null;
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test
	public void testVerify(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm[] algorithms = SignatureAlgorithm.values();
		SecureRandom random = new SecureRandom();
		int randomIndex = random.nextInt(algorithms.length);
		SignatureAlgorithm signatureAlgorithm = algorithms[randomIndex];
		//对数据进行签名
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		boolean isValid = DSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, signatureAlgorithm);
		Assert.assertTrue("数据签名验证失败!", isValid);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullData(){
		byte[] data = null;
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.SHA1WithDSA;
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		DSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, signatureAlgorithm);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullPublicKey(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.SHA1WithDSA;
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		byte[] publicKey = null;
		DSASignatureUtils.verify(data, publicKey, signature, signatureAlgorithm);
	}
	
	@Test
	public void testVerifyWithUnSuitablePublicKey(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.SHA1WithDSA;
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		byte[] publicKey = DSASignatureUtils.generateKeyPair().getPublicKey();
		boolean isValid = DSASignatureUtils.verify(data, publicKey, signature, signatureAlgorithm);
		Assert.assertFalse("在公钥不匹配的情况下，数据签名验证应该失败!", isValid);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullSignatureAlgorithm(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.SHA1WithDSA;
		byte[] signature = DSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		DSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, null);
	}
	
}
