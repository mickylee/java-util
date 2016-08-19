package com.mickyli.util.security;

import java.security.SecureRandom;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mickyli.util.java.security.KeyPairBean;
import com.mickyli.util.java.security.RSASignatureUtils;
import com.mickyli.util.java.security.RSAUtils;
import com.mickyli.util.java.security.RSASignatureUtils.SignatureAlgorithm;


/** 
 *   <B>说       明</B>:RSA数字签名工具测试类。
 * 
 */
public class RSASignatureUtilsTest {

	//测试使用的密钥对儿。
	private static KeyPairBean keyPair = null;

	@BeforeClass
	public static void init(){
		keyPair = RSAUtils.generateKeyPair();
	}

	@Test
	public void testSign(){
		byte[] data = "Wake me up when September ends.".getBytes();
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), SignatureAlgorithm.MD5WithRSA);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignWithNullData(){
		byte[] data = null;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), SignatureAlgorithm.MD5WithRSA);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignWithNullPrivateKey(){
		byte[] data = "Wake me up when September ends.".getBytes();
		byte[] privateKey = null;
		byte[] signature = RSASignatureUtils.sign(data, privateKey, SignatureAlgorithm.MD5WithRSA);
		Assert.assertNotNull("生成的数据签名不能为null!", signature);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignWithNullSignatureAlgorithm(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = null;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
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
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		boolean isValid = RSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, signatureAlgorithm);
		Assert.assertTrue("数据签名验证失败!", isValid);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullData(){
		byte[] data = null;
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.MD5WithRSA;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		RSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, signatureAlgorithm);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullPublicKey(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.MD5WithRSA;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		byte[] publicKey = null;
		RSASignatureUtils.verify(data, publicKey, signature, signatureAlgorithm);
	}
	
	@Test
	public void testVerifyWithUnSuitablePublicKey(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.MD5WithRSA;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		byte[] publicKey = RSAUtils.generateKeyPair().getPublicKey();
		boolean isValid = RSASignatureUtils.verify(data, publicKey, signature, signatureAlgorithm);
		Assert.assertFalse("在公钥不匹配的情况下，数据签名验证应该失败!", isValid);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullSignatureAlgorithm(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.MD5WithRSA;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		RSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testVerifyWithUnSuitableSignatureAlgorithm(){
		byte[] data = "Wake me up when September ends.".getBytes();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.MD5WithRSA;
		byte[] signature = RSASignatureUtils.sign(data, keyPair.getPrivateKey(), signatureAlgorithm);
		//对数据签名进行验证
		RSASignatureUtils.verify(data, keyPair.getPublicKey(), signature, SignatureAlgorithm.MD2WithRSA);
	}

}
