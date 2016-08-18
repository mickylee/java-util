package com.mickyli.util.j2se.security;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/** 
 *   <B>说       明</B>:RSA工具类。
 *
 *	 <p>该类提供基于RSA算法(Ron Rivest、Adi Shamir and Leonard Adleman)的加解密功能
 *      (非对称加密)。
 *   <p>该类基于JDK6实现，RSA算法支持如下:
 *   <li>密钥长度:默认1024 (支持512到16384位)。
 *   <li>工作模式:ECB。
 *   <li>填充方式:NoPadding、PKCS1Padding。
 *   
 *   <p>本类支持"私钥加密公钥解密"和"公钥加密私钥解密"方式。
 *   
 *   <p>注：如果使用NoPadding填充方式，解压后的数据会和原始数据不一致(数据长度)，需要进行额外处理。
 *   
 */
public class RSAUtils {

	/**
	 * RSA算法名称。
	 */
	private static final String ALGORITHM = "RSA";

	/**
	 * JDK6支持的RSA算法默认密钥长度。
	 */
	private static final int KEYSIZE = 1024;

	/**
	 * 默认的转换名称。格式: 算法名称/工作模式/填充方式
	 * 
	 */
	private static final String DEFAULT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

	/**
	 * 生成一个密钥对Bean(包括公钥和私钥)。
	 * 
	 * @param keySize 密钥长度(必须大于等于512且小于等于16384。)
	 * @return
	 *      密钥对。
	 * @throws IllegalArgumentException 如果密钥长度不合法。
	 * @throws IllegalStateException 如果系统不支持RSA算法。
	 */
	public static KeyPairBean generateKeyPair(int keySize){
		Assert.isTrue(keySize >= 512 && keySize <= 16384,
				"密钥长度必须大于等512且小于等于16384。");
		return EncryptionBase.generateKeyPair(ALGORITHM, keySize);
	}
	
	/**
	 * 生成一个密钥对Bean(包括公钥和私钥)。
	 * <p>使用默认密钥长度1024。
	 * 
	 * @return
	 *      密钥对。 
	 * @throws IllegalArgumentException 如果密钥长度不合法。
	 * @throws IllegalStateException 如果系统不支持RSA算法。
	 */
	public static KeyPairBean generateKeyPair(){
		return generateKeyPair(KEYSIZE);
	}

	/**
	 * 用私钥对数据进行加密。
	 * 
	 * @param data 待加密的数据。
	 * @param privateKey 私钥。
	 * @param transformation 转换名称。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何错误。
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey, String transformation){
		PrivateKey key = EncryptionBase.toPrivateKey(privateKey, ALGORITHM);
		return EncryptionBase.processData(data, key, transformation, Cipher.ENCRYPT_MODE, EmptyParameterSpec.getInstance());
	}
	
	/**
	 * 用私钥对数据进行加密。
	 * 
	 * @param data 待加密的数据。
	 * @param privateKey 私钥。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何错误。
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey){
		return encryptByPrivateKey(data, privateKey, DEFAULT_TRANSFORMATION);
	}
	
	/**
	 * 用私钥对字符串形式数据进行加密。
	 * <p>对加密结果进行Base64编码。
	 * 
	 * @param dataString 待加密的字符串形式的数据。
	 * @param privateKey 私钥。
	 * @return
	 *      加密后的结果。
	 */
	public static String encryptByPrivateKey(String dataString, byte[] privateKey){
		Assert.notNull(dataString, "待加密的数据不能为null!");
		byte[] result = encryptByPrivateKey(dataString.getBytes(SystemUtils.DEFAULT_CHARSET), privateKey);
		return Base64Utils.encode2string(result);
	}

	/**
	 * 用公钥对数据进行解密。
	 * 
	 * @param data 待解密的数据。
	 * @param publicKey 公钥。
	 * @param transformation 转换名称。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何错误。
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey, String transformation){
		PublicKey key = EncryptionBase.toPublicKey(publicKey, ALGORITHM);
		return EncryptionBase.processData(data, key, transformation, Cipher.DECRYPT_MODE, EmptyParameterSpec.getInstance());
	}
	
	/**
	 * 用公钥对数据进行解密。
	 * 
	 * @param data 待解密的数据。
	 * @param publicKey 公钥。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何错误。
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey){
		return decryptByPublicKey(data, publicKey, DEFAULT_TRANSFORMATION);
	}
	
	/**
	 * 用公钥对字符串形式数据进行解密。
	 * <p>解密之前先对数据进行Base64解码。
	 * 
	 * @param dataString 待解密的字符串形式的数据。
	 * @param publicKey 公钥。
	 * @return
	 *      解密后的数据。
	 */
	public static String decryptByPublicKey(String dataString, byte[] publicKey){
		byte[] data = Base64Utils.decode(dataString);
		byte[] result = decryptByPublicKey(data, publicKey);
		return new String(result,SystemUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * 用公钥对数据进行加密。
	 * 
	 * @param data 待加密的数据。
	 * @param publicKey 公钥。
	 * @param transformation 转换名称。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何错误。
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey, String transformation){
		PublicKey key = EncryptionBase.toPublicKey(publicKey, ALGORITHM);
		return EncryptionBase.processData(data, key, transformation, Cipher.ENCRYPT_MODE, EmptyParameterSpec.getInstance());
	}
	
	/**
	 * 用公钥对数据进行加密。
	 * 
	 * @param data 待加密的数据。
	 * @param publicKey 公钥。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何错误。
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey){
		PublicKey key = EncryptionBase.toPublicKey(publicKey, ALGORITHM);
		return EncryptionBase.processData(data, key, DEFAULT_TRANSFORMATION, Cipher.ENCRYPT_MODE, EmptyParameterSpec.getInstance());
	}
	
	/**
	 * 用私钥对数据进行解密。
	 * 
	 * @param data 待解密的数据。
	 * @param privateKey 私钥。
	 * @param transformation 转换名称。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何错误。
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey, String transformation){
		PrivateKey key = EncryptionBase.toPrivateKey(privateKey, ALGORITHM);
		return EncryptionBase.processData(data, key, transformation, Cipher.DECRYPT_MODE, EmptyParameterSpec.getInstance());
	}
	
	/**
	 * 用私钥对数据进行解密。
	 * 
	 * @param data 待解密的数据。
	 * @param privateKey 私钥。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何错误。
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey){
		PrivateKey key = EncryptionBase.toPrivateKey(privateKey, ALGORITHM);
		return EncryptionBase.processData(data, key, DEFAULT_TRANSFORMATION, Cipher.DECRYPT_MODE, EmptyParameterSpec.getInstance());
	}

	private RSAUtils(){}

}
