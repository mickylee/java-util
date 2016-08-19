package com.mickyli.util.security;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.crypto.SecretKey;




import org.junit.Assert;
import org.junit.Test;

import com.mickyli.util.java.security.DHUtils;
import com.mickyli.util.java.security.KeyPairBean;

/** 
 *   <B>说       明</B>:DH工具测试类。
 *
 */
public class DHUtilsTest {

	@Test
	public void testGenerateKeyPair(){
		int keySize = 512;
		KeyPairBean bean1 = DHUtils.generateKeyPair(keySize);
		KeyPairBean bean2 = DHUtils.generateKeyPair(keySize);
		Assert.assertTrue("随机生成的两个密钥对不能相同!", !bean1.equals(bean2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyPairWithWrongKeySize(){
		int keySize = 123;
		DHUtils.generateKeyPair(keySize);
	}

	@Test
	public void testGenerateKeyPairWithPublicKey(){
		KeyPairBean otherBean = DHUtils.generateKeyPair();
		KeyPairBean ourBean = DHUtils.generateKeyPair(otherBean.getPublicKey());
		//判断我方和对方产生的本地密钥是否相同
		try {
			Method method = DHUtils.class.getDeclaredMethod("getSecretKey", byte[].class, byte[].class);
			method.setAccessible(true);
			//我方本地密钥
			SecretKey ourKey = (SecretKey) method.invoke(null, otherBean.getPublicKey(), ourBean.getPrivateKey());
			//对方本地密钥
			SecretKey otherKey = (SecretKey) method.invoke(null, ourBean.getPublicKey(), otherBean.getPrivateKey());
			Assert.assertTrue("我方和对方的本地密钥不相等!", ourKey.equals(otherKey));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} 
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyPairWithNullPublicKey(){
		byte[] publicKey = null;
		DHUtils.generateKeyPair(publicKey);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyPairWithIllegalPublicKey(){
		KeyPairBean otherBean = DHUtils.generateKeyPair();
		DHUtils.generateKeyPair(otherBean.getPrivateKey());
	}

	@Test
	public void testEncrypt(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方公钥。
		byte[] ourPublicKey = ourBean.getPublicKey();
		//获取我方私钥。
		byte[] ourPrivateKey = ourBean.getPrivateKey();
		//对方根据我方公钥生成密钥。
		KeyPairBean otherBean = DHUtils.generateKeyPair(ourPublicKey);
		//获取对方公钥。
		byte[] otherPublicKey = otherBean.getPublicKey();
		//我方对数据加密。
		byte[] data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.".getBytes();
		byte[] result = DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
		Assert.assertNotNull("加密后的结果不能为null!", result);
		Assert.assertTrue("加密后的数据不能和原始数据相等!", !Arrays.equals(data, result));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullData(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方公钥。
		byte[] ourPublicKey = ourBean.getPublicKey();
		//获取我方私钥。
		byte[] ourPrivateKey = ourBean.getPrivateKey();
		//对方根据我方公钥生成密钥。
		KeyPairBean otherBean = DHUtils.generateKeyPair(ourPublicKey);
		//获取对方公钥。
		byte[] otherPublicKey = otherBean.getPublicKey();
		//我方对数据加密。
		byte[] data = null;
		DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullPublicKey(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方私钥。
		byte[] ourPrivateKey = ourBean.getPrivateKey();
		//获取对方公钥。
		byte[] otherPublicKey = null;
		//我方对数据加密。
		byte[] data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.".getBytes();
		DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithIllegalPublicKey(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方私钥。
		byte[] ourPrivateKey = ourBean.getPrivateKey();
		//获取对方公钥。
		byte[] otherPublicKey = {1,2,3};
		//我方对数据加密。
		byte[] data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.".getBytes();
		DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithNullPrivateKey(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方公钥。
		byte[] ourPublicKey = ourBean.getPublicKey();
		//获取我方私钥。
		byte[] ourPrivateKey = null;
		//对方根据我方公钥生成密钥。
		KeyPairBean otherBean = DHUtils.generateKeyPair(ourPublicKey);
		//获取对方公钥。
		byte[] otherPublicKey = otherBean.getPublicKey();
		//我方对数据加密。
		byte[] data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.".getBytes();
		DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncryptWithIllegalPrivateKey(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方公钥。
		byte[] ourPublicKey = ourBean.getPublicKey();
		//获取我方私钥。
		byte[] ourPrivateKey = {1,2,3};
		//对方根据我方公钥生成密钥。
		KeyPairBean otherBean = DHUtils.generateKeyPair(ourPublicKey);
		//获取对方公钥。
		byte[] otherPublicKey = otherBean.getPublicKey();
		//我方对数据加密。
		byte[] data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.".getBytes();
		DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
	}

	@Test
	public void testDecrypt(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方公钥。
		byte[] ourPublicKey = ourBean.getPublicKey();
		//获取我方私钥。
		byte[] ourPrivateKey = ourBean.getPrivateKey();
		//对方根据我方公钥生成密钥。
		KeyPairBean otherBean = DHUtils.generateKeyPair(ourPublicKey);
		//获取对方公钥。
		byte[] otherPublicKey = otherBean.getPublicKey();
		//获取对方私钥。
		byte[] otherPrivateKey = otherBean.getPrivateKey();
		//我方对数据加密。
		byte[] data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.".getBytes(Charset.forName("utf-8"));
		System.out.println("DHUtilsTest => testDecrypt => 我方加密前的数据:"+new String(data,Charset.forName("utf-8")));
		byte[] result = DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
		System.out.println("DHUtilsTest => testDecrypt => 我方加密后的数据:"+new String(result,Charset.forName("utf-8")));
		//对方对数据进行解密
		byte[] dData = DHUtils.decrypt(result, ourPublicKey, otherPrivateKey);
		System.out.println("DHUtilsTest => testDecrypt => 对方解密后的数据:"+new String(dData,Charset.forName("utf-8")));
		Assert.assertNotNull("加密后的数据不能为null!", result);
		Assert.assertTrue("加密后的数据不能和原始数据相等!", !Arrays.equals(data, result));
		Assert.assertTrue("解密后的数据与加密前数据不相等!", Arrays.equals(data, dData));
	}
	
	@Test
	public void testDecryptString(){
		//双方交换密钥==============
		//我方生成密钥。
		KeyPairBean ourBean = DHUtils.generateKeyPair();
		//获取我方公钥。
		byte[] ourPublicKey = ourBean.getPublicKey();
		//获取我方私钥。
		byte[] ourPrivateKey = ourBean.getPrivateKey();
		//对方根据我方公钥生成密钥。
		KeyPairBean otherBean = DHUtils.generateKeyPair(ourPublicKey);
		//获取对方公钥。
		byte[] otherPublicKey = otherBean.getPublicKey();
		//获取对方私钥。
		byte[] otherPrivateKey = otherBean.getPrivateKey();
		//我方对数据加密。
		String data = "穷街-Ricky was a young boy,He had a heart of stone.Lived 9 to 5 and worked his fingers to the bone.";
		System.out.println("DHUtilsTest => testDecrypt => 我方加密前的数据:"+data);
		String result = DHUtils.encrypt(data, otherPublicKey, ourPrivateKey);
		System.out.println("DHUtilsTest => testDecrypt => 我方加密后的数据:"+result);
		//对方对数据进行解密
		String dData = DHUtils.decrypt(result, ourPublicKey, otherPrivateKey);
		System.out.println("DHUtilsTest => testDecrypt => 对方解密后的数据:"+dData);
		Assert.assertNotNull("加密后的数据不能为null!", result);
		Assert.assertTrue("加密后的数据不能和原始数据相等!", !data.equals(result));
		Assert.assertTrue("解密后的数据与加密前数据不相等!", data.equals(dData));
	}

}
