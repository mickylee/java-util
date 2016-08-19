package com.mickyli.util.java.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/** 
 *   <B>说       明</B>:PBEUtils工具类。
 *   
 *   <p>该类提供基于PBE(Password Base Encryption，基于口令加密)算法的加解密功能(对称加密)。
 *   <p>该类基于JDK6实现，PBE算法支持如下:
 *   <li>算法:PBEWithMD5AndDES(支持密钥长度56,默认56)、PBEWithMD5AndTripleDES(支持密钥长度112、168,默认168)、PBEWithSHA1AndDESede(支持密钥长度112、168,默认168)、PBEWithSHA1AndRC2_40(支持密钥长度40-1024,默认128)。
 *   <li>工作模式:CBC。
 *   <li>填充方式:PKCS5Padding。
 *
 */
public class PBEUtils {
	
	/**
	 * 迭代计数。
	 */
	private static final int ITERATIONCOUNT = 200;
	
	/**
	 * 默认的盐。
	 */
	private static final byte[] DEFAULT_SALT = {1,-1,1,-1,1,-1,1,-1};
	
	/**
	 * 随机生成盐。
	 * 
	 * @return
	 *      盐(8位字节数组)
	 */
	public static byte[] generateSalt(){
		SecureRandom random = new SecureRandom();
		//盐必须为8位字节数组。
		return random.generateSeed(8);
	}
	
	/**
	 * 对数据进行加密处理。
	 * 
	 * @param data 待加密的数据。
	 * @param password 密码。
	 * @param salt 盐。
	 * @param algorithm PBE算法，参见{@link PBEAlgorithm}。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, String password, byte[] salt, PBEAlgorithm algorithm){
		Assert.notNull(password, "password不能为null!");
		Assert.notNull(algorithm, "PBE算法不能为null!");
		Assert.notNull(salt, "盐不能为null!");
		Assert.isTrue(salt.length == 8, "盐的字节数组长度必须为8位!");
		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);
		Key key = toKey(password,algorithm.getName());
		return EncryptionBase.processData(data, key, algorithm.getName(), Cipher.ENCRYPT_MODE, pbeParameterSpec);
	}
	
	/**
	 * 对数据进行加密处理。
	 * <p>默认使用PBEWithMD5AndDES算法。
	 * 
	 * @param data 待加密的数据。
	 * @param password 密码。
	 * @param salt 盐。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, String password, byte[] salt){
		return encrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
	}
	
	/**
	 * 对数据进行加密处理。
	 * 
	 * @param data 待加密的数据。
	 * @param password 密码。
	 * @param algorithm PBE算法，参见{@link PBEAlgorithm}。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, String password, PBEAlgorithm algorithm){
		return encrypt(data, password, DEFAULT_SALT, algorithm);
	}
	
	/**
	 * 对数据进行加密处理。
	 * <p>默认使用PBEWithMD5AndDES算法。
	 * 
	 * @param data 待加密的数据。
	 * @param password 密码。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, String password){
		return encrypt(data, password, DEFAULT_SALT, PBEAlgorithm.PBEWithMD5AndDES);
	}
	
	/**
	 * 对数据进行加密处理。
	 * <p>默认使用PBEWithMD5AndDES算法。
	 * <p>对返回结果进行Base64编码处理。
	 * 
	 * @param dataString 待加密的数据。
	 * @param password 密码。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static String encrypt(String dataString, String password){
		Assert.notNull(dataString, "待加密数据不能为null!");
		byte[] data = dataString.getBytes(SystemUtils.DEFAULT_CHARSET);
		byte[] result = encrypt(data, password);
		return Base64Utils.encode2string(result);
	}
	
	/**
	 * 对数据进行解密处理。
	 * 
	 * @param data 待解密的数据。
	 * @param password 密码。
	 * @param salt 盐。
	 * @param algorithm PBE算法，参见{@link PBEAlgorithm}。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, String password, byte[] salt, PBEAlgorithm algorithm){
		Assert.notNull(password, "password不能为null!");
		Assert.notNull(algorithm, "PBE算法不能为null!");
		Assert.notNull(salt, "盐不能为null!");
		Assert.isTrue(salt.length == 8, "盐的字节数组长度必须为8位!");
		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);
		Key key = toKey(password,algorithm.getName());
		return EncryptionBase.processData(data, key, algorithm.getName(), Cipher.DECRYPT_MODE, pbeParameterSpec);
	}
	
	/**
	 * 对数据进行解密处理。
	 * <p>默认使用PBEWithMD5AndDES算法。
	 * 
	 * @param data 待解密的数据。
	 * @param password 密码。
	 * @param salt 盐。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, String password, byte[] salt){
		return decrypt(data, password, salt, PBEAlgorithm.PBEWithMD5AndDES);
	}
	
	/**
	 * 对数据进行解密处理。
	 * 
	 * @param data 待解密的数据。
	 * @param password 密码。
	 * @param algorithm PBE算法，参见{@link PBEAlgorithm}。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, String password, PBEAlgorithm algorithm){
		return decrypt(data, password, DEFAULT_SALT, algorithm);
	}
	
	/**
	 * 对数据进行解密处理。
	 * <p>默认使用PBEWithMD5AndDES算法。
	 * 
	 * @param data 待解密的数据。
	 * @param password 密码。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, String password){
		return decrypt(data, password, DEFAULT_SALT, PBEAlgorithm.PBEWithMD5AndDES);
	}
	
	/**
	 * 对数据进行解密处理。
	 * <p>默认使用PBEWithMD5AndDES算法。
	 * <p>对待解密数据进行Base64解码处理。
	 * 
	 * @param dataString 待解密的数据。
	 * @param password 密码。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static String decrypt(String dataString, String password){
		Assert.notNull(dataString, "待解密数据不能为null!");
		byte[] data = Base64Utils.decode(dataString);
		byte[] result = decrypt(data, password);
		return new String(result, SystemUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * 将密码转化成密钥。
	 * 
	 * @param password 密码。
	 * @param algorithm PBE算法，参见{@link PBEAlgorithm}。
	 * @return
	 *      密钥。
	 * @throws IllegalArgumentException 如果不支持algorithm算法或者由密码产生密钥材料不合法!。
	 */
	private static Key toKey(String password,String algorithm) {
		try {
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
			return keyFactory.generateSecret(pbeKeySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e);
		}
	}


	/** 
	 * PBE算法，这里只列举JDK支持的算法。
	 */
	public static enum PBEAlgorithm{
		
		PBEWithMD5AndDES("PBEWithMD5AndDES",56),
		PBEWithMD5AndTripleDES("PBEWithMD5AndTripleDES",168),
		PBEWithSHA1AndDESede("PBEWithSHA1AndDESede",168),
		PBEWithSHA1AndRC2_40("PBEWithSHA1AndRC2_40",128);
		
		/**
		 * 算法名称。
		 */
		private String name;
		
		/**
		 * 默认密钥长度。
		 */
		private int defaultKeySize;
		
		private PBEAlgorithm(String name, int defaultKeySize) {
			this.name = name;
			this.defaultKeySize = defaultKeySize;
		}

		public String getName() {
			return name;
		}

		public int getDefaultKeySize() {
			return defaultKeySize;
		}
		
	}

	private PBEUtils(){}
	
}
