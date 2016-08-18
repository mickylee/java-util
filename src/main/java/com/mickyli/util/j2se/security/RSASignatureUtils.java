package com.mickyli.util.j2se.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/** 
 *   <B>说       明</B>:RSA数字签名工具类。
 *   <p>本类中的数字签名算法基于RSA，支持的算法参见{@link SignatureAlgorithm}
 *   <p>产生公私钥的部分请使用{@link RSAUtils}
 *   
 *   @see SignatureAlgorithm
 *   @see RSAUtils
 */
public class RSASignatureUtils {

	/**
	 * RSA算法名称。
	 */
	private static final String ALGORITHM = "RSA";

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
	 * RSA数字签名算法。
	 * 这里只列举JDK6支持的算法。
	 */
	public static enum SignatureAlgorithm{

		MD2WithRSA("MD2WithRSA"),
		MD5WithRSA("MD5WithRSA"),
		SHA1WithRSA("SHA1WithRSA"),
		SHA256WithRSA("SHA256WithRSA"),
		SHA384WithRSA("SHA384WithRSA"),
		SHA512WithRSA("SHA512WithRSA");

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

	private RSASignatureUtils(){}

}
