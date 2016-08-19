package com.mickyli.util.java.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/** 
 *   <B>说       明</B>:DSA数字签名工具类。
 *   <p>基于DSA算法的数字签名工具类。
 *   
 *   @see SignatureAlgorithm
 *
 */
public class DSASignatureUtils {

	/**
	 * DSA算法名称。
	 */
	private static final String ALGORITHM = "DSA";
	
	/**
	 * JDK6支持的DSA算法默认密钥长度。
	 */
	private static final int KEYSIZE = 1024;
	
	/**
	 * 生成一个密钥对Bean(包括公钥和私钥)。
	 * 
	 * @param keySize 密钥长度(必须大于等于512且小于等于1024，同时是64的倍数。)
	 * @return
	 *      密钥对。
	 * @throws IllegalArgumentException 如果密钥长度不合法。
	 * @throws IllegalStateException 如果系统不支持DSA算法。
	 */
	public static KeyPairBean generateKeyPair(int keySize){
		Assert.isTrue(keySize >= 512 && keySize <= 1024 && keySize % 64 == 0 ,
				"密钥长度必须大于等512且小于等于1024，同时是64的倍数!");
		return EncryptionBase.generateKeyPair(ALGORITHM, keySize);
	}
	
	/**
	 * 生成一个密钥对Bean(包括公钥和私钥)。
	 * <p>使用默认密钥长度1024。
	 * 
	 * @return
	 *      密钥对。
	 * @throws IllegalArgumentException 如果密钥长度不合法。
	 * @throws IllegalStateException 如果系统不支持DSA算法。
	 */
	public static KeyPairBean generateKeyPair(){
		return generateKeyPair(KEYSIZE);
	}

	/**
	 * 对数据进行签名。
	 * 
	 * @param data 待签名的数据。
	 * @param privateKey 私钥。
	 * @param signatureAlgorithm 数字签名算法。 参见{@link SignatureAlgorithm}
	 * @return
	 *      数字签名。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在签名过程中发生错误。
	 */
	public static byte[] sign(byte[] data, byte[] privateKey, SignatureAlgorithm signatureAlgorithm){
		Assert.notNull(data, "要签名的数据不能为null!");
		Assert.notNull(signatureAlgorithm, "数字签名算法不能为null!");
		try {
			PrivateKey key = EncryptionBase.toPrivateKey(privateKey, ALGORITHM);
			Signature signature = Signature.getInstance(signatureAlgorithm.getName());
			signature.initSign(key);
			signature.update(data);
			return signature.sign();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		} catch (SignatureException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * 对数据的数字签名进行验证。
	 * 
	 * @param data 待验证数据。
	 * @param publicKey 公钥。
	 * @param sign data的数据签名。
	 * @param signatureAlgorithm 数字签名算法。参见{@link SignatureAlgorithm}
	 * @return
	 *      签名是否有效。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在验证过程中发生错误。
	 */
	public static boolean verify(byte[] data, byte[] publicKey, byte[] sign, SignatureAlgorithm signatureAlgorithm){
		Assert.notNull(data, "要验证的数据不能为null!");
		Assert.notNull(sign, "数字签名不能为null!");
		Assert.notNull(signatureAlgorithm, "数字签名算法不能为null!");
		try {
			PublicKey key = EncryptionBase.toPublicKey(publicKey, ALGORITHM);
			Signature signature = Signature.getInstance(signatureAlgorithm.getName());
			signature.initVerify(key);
			signature.update(data);
			return signature.verify(sign);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		} catch (SignatureException e) {
			throw new IllegalStateException(e);
		}
	}

	/** 
	 * DSA数字签名算法。
	 * 这里只列举JDK6支持的算法。
	 */
	public static enum SignatureAlgorithm{

		SHA1WithDSA("SHA1WithDSA");

		/**
		 * 算法名称。
		 */
		private String name;

		private SignatureAlgorithm(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	private DSASignatureUtils(){}
	
}
