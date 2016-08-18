package com.mickyli.util.j2se.security;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/** 
 *   <B>说       明</B>:DESede工具类。
 *   
 *   <p>该类提供基于DESede(三重DES)算法的加解密功能(对称加密)。
 *   <p>该类基于JDK6实现，DESede算法支持如下:
 *   <li>密钥长度:默认168(支持112、168)。
 *   <li>工作模式:ECB、CBC、PCBC、CTR、CTS、CFB、CFB(8、16、32、40、48、56、64)和OFB、OFB(8、16、32、40、48、56、64)等。
 *   <li>填充方式:NoPadding、PKCS5Padding和ISO10126Padding。
 *   
 *   <p>注:如果使用CTR和CTS工作模式，填充方式必须使用NoPadding。否则会产生NoSuchPaddingException异常!
 *
 */
public class DESedeUtils {
	
	/**
	 * DESede算法名称。
	 */
	private static final String ALGORITHM = "DESede";
	
	/**
	 * JDK6支持的DESede算法默认密钥长度。
	 */
	private static final int KEYSIZE = 168;
	
	/**
	 * 默认的转换名称。格式: 算法名称/工作模式/填充方式
	 * 
	 */
	private static final String DEFAULT_TRANSFORMATION = "DESede/CBC/PKCS5Padding";
	
	/**
	 * 默认的初始化向量。在CBC等模式中的会使用初始化向量。
	 */
	private static final byte[] DEFAULT_IV = {1,-1,1,-1,1,-1,1,-1};
	
	/**
	 * 随机生成一个字节数组形式的密钥。
	 * 
	 * @return
	 *      字节数组形式的密钥。
	 */
	public static byte[] generateKey(){
		return EncryptionBase.generateKey(ALGORITHM, KEYSIZE);
	}
	
	/**
	 * 对数据进行加密处理。
	 * 
	 * @param data 待加密的数据。
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @param transformation 转换名称，例如 DESede/CBC/PKCS5Padding。
	 * @param iv 初始化向量(数组长度必须等于8位)。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String transformation, byte[] iv){
		Assert.notNull(iv, "初始化向量不能为空!");
		Assert.isTrue(iv.length == 8, "初始化向量的数组长度必须为8位!");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		return EncryptionBase.processData(data, toKey(key), transformation, Cipher.ENCRYPT_MODE, ivParameterSpec);
	}
	
	/**
	 * 对数据进行加密处理。
	 * 
	 * @param data 待加密的数据。
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @param transformation 转换名称，例如 DESede/CBC/PKCS5Padding。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String transformation){
		return encrypt(data, key, transformation, DEFAULT_IV);
	}
	
	/**
	 * 对数据进行加密处理。
	 * 使用默认的转换名称:DESede/CBC/PKCS5Padding。
	 * 
	 * @param data 待加密的数据。
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static byte[] encrypt(byte[] data, byte[] key){
		return encrypt(data, key, DEFAULT_TRANSFORMATION);
	}
	
	/**
	 * 对数据进行加密处理。
	 * 使用默认的转换名称:DESede/CBC/PKCS5Padding。
	 * 
	 * 将加密数据用Base64编码处理。
	 * 将字符串形式密钥用SHA256进行处理。
	 * 
	 * @param dataString 待加密的数据。
	 * @param keyString 字符串形式的密钥。
	 * @return
	 *      加密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在加密过程中发生任何异常。
	 */
	public static String encrypt(String dataString, String keyString){
		Assert.notNull(dataString, "待加密数据不能为null!");
		Assert.notNull(keyString, "字符串形式的密钥不能为null!");
		byte[] data = dataString.getBytes(SystemUtils.DEFAULT_CHARSET);
		byte[] key = MessageDigestUtils.getSHA256Digest(keyString.getBytes(SystemUtils.DEFAULT_CHARSET));
		byte[] result = encrypt(data, key, DEFAULT_TRANSFORMATION);
		return Base64Utils.encode2string(result);
	}
	
	/**
	 * 对数据进行解密处理。
	 * 
	 * @param data 待解密的数据。
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @param transformation 转换名称，例如 DESede/CBC/PKCS5Padding。
	 * @param iv 初始化向量(数组长度必须等于8位)。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String transformation, byte[] iv){
		Assert.notNull(iv, "初始化向量不能为空!");
		Assert.isTrue(iv.length == 8, "初始化向量的数组长度必须为8位!");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		return EncryptionBase.processData(data, toKey(key), transformation, Cipher.DECRYPT_MODE, ivParameterSpec);
	}
	
	/**
	 * 对数据进行解密处理。
	 * 
	 * @param data 待解密的数据。
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @param transformation 转换名称，例如 DESede/CBC/PKCS5Padding。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String transformation){
		return decrypt(data, key, transformation, DEFAULT_IV);
	}
	
	/**
	 * 对数据进行解密处理。
	 * 使用默认的转换名称:DESede/CBC/PKCS5Padding。
	 * 
	 * @param data 待解密的数据。
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何异常。
	 */
	public static byte[] decrypt(byte[] data, byte[] key){
		return decrypt(data, key, DEFAULT_TRANSFORMATION);
	}
	
	/**
	 * 对数据进行解密处理。
	 * 使用默认的转换名称:DESede/CBC/PKCS5Padding。
	 * 
	 * 将待解密数据用Base64解码处理。
	 * 将字符串形式密钥用SHA256进行处理。
	 * 
	 * @param dataString 待解密的数据。
	 * @param keyString 字符串形式的密钥。
	 * @return
	 *      解密后的数据。
	 * @throws IllegalArgumentException 如果参数不合法。
	 * @throws IllegalStateException 如果在解密过程中发生任何异常。
	 */
	public static String decrypt(String dataString, String keyString){
		Assert.notNull(dataString, "待解密数据不能为null!");
		Assert.notNull(keyString, "字符串形式的密钥不能为null!");
		byte[] data = Base64Utils.decode(dataString);
		byte[] key = MessageDigestUtils.getSHA256Digest(keyString.getBytes(SystemUtils.DEFAULT_CHARSET));
		byte[] result = decrypt(data, key, DEFAULT_TRANSFORMATION);
		return new String(result, SystemUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * 将字节数组形式的密钥转换成密钥对象。
	 * 
	 * @param key 字节数组形式的密钥(数组长度不能小于24位)。
	 * @return
	 *      密钥对象。
	 * @throws IllegalArgumentException 如果给定的密钥内容少于24个字节。
	 */
	private static Key toKey(byte[] key){
		try {
			Assert.notNull(key, "字节形式的密钥不能为null!");
			Assert.isTrue(key.length >= 24, "字节形式密钥的数组长度不能小于24位!");
			DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
			return EncryptionBase.toKey(deSedeKeySpec, ALGORITHM);
		} catch (InvalidKeyException e) {
			//never here
			throw new IllegalArgumentException(e);
		}
	}

	private DESedeUtils(){}
	
}
