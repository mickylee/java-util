package com.mickyli.util.j2se.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/** 
 *   <B>说       明</B>:DH工具类。
 *   <p>基于DH(Diffie-Hellman算法——密钥交换算法)的工具类。
 *   <p>DH算法本身在于安全的交换密钥，并不能进行加密或者解密数据。
 *   <p>甲方生成密钥对儿(公钥和私钥)后，将公钥发布给乙方。乙方根据甲方的公钥生成乙方的密钥对儿。乙方将公钥发布给甲方，这样就完成了密钥交换。
 *   <p>甲方根据自己的私钥和乙方的公钥可生成本地密钥，同理乙方根据自己的私钥和甲方的公钥可生成本地密钥。甲方和乙方的本地密钥应该相同，所以甲方乙方可以以其他对称加密方式用本地密钥对消息进行加密。
 *   <p>本类中采用AES算法提供对数据加解密的支持。
 *   加解密过程需要在双方安全交换密钥后进行。
 *
 */
public class DHUtils {

	/**
	 * DH算法名称。
	 */
	private static final String ALGORITHM = "DH";

	/**
	 * 默认密钥长度。
	 */
	private static final int DEFAULT_KEYSIZE = 1024;

	/**
	 * 数据加密的算法名称。
	 */
	private static final String KEY_SECRET_ALGORITHM = "AES";

	/**
	 * 生成一个密钥对Bean(包括公钥和私钥)。
	 * 
	 * @param keySize 密钥长度(必须大于等于512且小于等于1024，同时是64的倍数。)
	 * @return
	 *      密钥对。
	 * @throws IllegalArgumentException 如果密钥长度不合法。
	 * @throws IllegalStateException 如果系统不支持DH算法。
	 */
	public static KeyPairBean generateKeyPair(int keySize){
		Assert.isTrue(keySize >= 512 && keySize <= 1024 && keySize % 64 == 0 ,
				"密钥长度必须大于等512且小于等于1024，同时是64的倍数!");
		return EncryptionBase.generateKeyPair(ALGORITHM, keySize);
	}

	/**
	 * 生成一个密钥对Bean(包括公钥和私钥)。
	 * <p>采用默认密钥长度1024。
	 * 
	 * @return
	 *      密钥对 。
	 * @throws IllegalArgumentException 如果密钥长度不合法。
	 * @throws IllegalStateException 如果系统不支持DH算法。
	 */
	public static KeyPairBean generateKeyPair(){
		return generateKeyPair(DEFAULT_KEYSIZE);
	}

	/**
	 * 根据公钥生成密钥对。
	 * <p>用于当对方发布公钥后，我方可根据对方的公钥生成我方自己的密钥对。
	 * 
	 * @param publicKey 公钥。
	 * @return
	 *      密钥对。
	 * @throws IllegalArgumentException 如果公钥为null或不合法。
	 * @throws IllegalStateException 如果系统不支持DH算法。
	 */
	public static KeyPairBean generateKeyPair(byte[] publicKey){
		KeyPair keyPair = doGenerateKeyPair(publicKey);
		PublicKey publickey = keyPair.getPublic();
		PrivateKey privatekey = keyPair.getPrivate();
		return new KeyPairBean(publickey.getEncoded(), privatekey.getEncoded());
	}

	/**
	 * 根据公钥生成密钥对。
	 * <p>用于当甲方发布公钥后，乙方可根据甲方的公钥生成乙方自己的密钥对。
	 * 
	 * @param publicKey 公钥。
	 * @return
	 *      密钥对。
	 * @throws IllegalArgumentException 如果公钥为null或不合法。
	 * @throws IllegalStateException 如果系统不支持DH算法。
	 */
	private static KeyPair doGenerateKeyPair(byte[] publicKey){
		Assert.notNull(publicKey, "公钥不能为null!");
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			DHPublicKey dhPublicKey = (DHPublicKey) keyFactory.generatePublic(keySpec);
			DHParameterSpec dhParameterSpec = dhPublicKey.getParams();
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
			keyPairGenerator.initialize(dhParameterSpec);
			return keyPairGenerator.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 对数据进行加密。
	 * 
	 * @param data 待加密的数据。
	 * @param publicKey 对方的公钥。
	 * @param privateKey 我方的私钥。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException - 如果参数不合法。
	 * @throws IllegalStateException - 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, byte[] publicKey, byte[] privateKey){
		SecretKey secretKey = getSecretKey(publicKey, privateKey);
		/*
		 * 利用AES算法完成加密过程。由于AES要求密钥长度为16位，所以这里
		 * 取密钥内容的MD5值。
		 */
		byte[] key = MessageDigestUtils.getMD5Digest(secretKey.getEncoded());
		return AESUtils.encrypt(data, key);
	}
	
	/**
	 * 对字符串型数据进行加密。
	 * 将加密结果用Base64编码处理。
	 * 
	 * @param dataString 待加密的数据。
	 * @param publicKey 对方的公钥。
	 * @param privateKey 我方的私钥。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException - 如果参数不合法。
	 * @throws IllegalStateException - 如果在加密过程中发生任何异常。
	 */
	public static String encrypt(String dataString, byte[] publicKey, byte[] privateKey){
		Assert.notNull(dataString, "待加密数据不能为null!");
		byte[] data = dataString.getBytes(SystemUtils.DEFAULT_CHARSET);
		byte[] result = encrypt(data, publicKey, privateKey);
		return Base64Utils.encode2string(result);
	}

	/**
	 * 对数据进行解密。
	 * 
	 * @param data 待解密的数据。
	 * @param publicKey 对方的公钥。
	 * @param privateKey 我方的私钥。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException - 如果参数不合法。
	 * @throws IllegalStateException - 如果在解密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, byte[] publicKey, byte[] privateKey){
		SecretKey secretKey = getSecretKey(publicKey, privateKey);
		/*
		 * 利用AES算法完成加密过程。由于AES要求密钥长度为16位，所以这里
		 * 取密钥内容的MD5值。
		 */
		byte[] key = MessageDigestUtils.getMD5Digest(secretKey.getEncoded());
		return AESUtils.decrypt(data, key);
	}
	
	/**
	 * 对字符串型数据进行解密。
	 * 将待解密数据用Base64解码处理。
	 * 
	 * @param dataString 待解密的数据。
	 * @param publicKey 对方的公钥。
	 * @param privateKey 我方的私钥。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException - 如果参数不合法。
	 * @throws IllegalStateException - 如果在解密过程中发生任何异常。
	 */
	public static String decrypt(String dataString, byte[] publicKey, byte[] privateKey){
		Assert.notNull(dataString, "待解密数据不能为null!");
		byte[] data = Base64Utils.decode(dataString);
		byte[] result = decrypt(data, publicKey, privateKey);
		return new String(result, SystemUtils.DEFAULT_CHARSET);
	}

	/**
	 * 通过对方的公钥和我方的私钥构建密钥(即本地密钥)。
	 * 
	 * @param publicKey 对方的公钥。
	 * @param privateKey 我方的私钥。
	 * @return
	 *      密钥(用于实际加解密的本地密钥)。
	 * @throws IllegalArgumentException 如果密钥为null或不合法或者密钥材料不合法。
	 * @throws IllegalStateException 如果系统不支持相应的算法。    
	 */
	private static SecretKey getSecretKey(byte[] publicKey, byte[] privateKey){
		Assert.notNull(publicKey, "公钥不能为null!");
		Assert.notNull(privateKey, "私钥不能为null!");
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			//产生公钥。
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
			//产生私钥。
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
			//生成本地密钥。
			KeyAgreement keyAgreement = KeyAgreement.getInstance(ALGORITHM);
			keyAgreement.init(priKey);
			keyAgreement.doPhase(pubKey, true);
			return keyAgreement.generateSecret(KEY_SECRET_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private DHUtils(){}

}
