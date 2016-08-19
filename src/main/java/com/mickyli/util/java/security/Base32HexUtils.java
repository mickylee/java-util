package com.mickyli.util.java.security; 

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/** 
 *   <B>说       明</B>:Base32(Hex)工具类。
 *   
 */
public class Base32HexUtils {

	/**
	 * 对数据进行Base32(Hex)编码。
	 * 
	 * @param data 待编码的数据。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static byte[] encode(byte[] data){
		Assert.notNull(data, "要进行编码的数据不能为null!");
		Base32Encoder encoder = new Base32Encoder(true);
		return encoder.encode(data);
	}
	
	/**
	 * 对数据进行Base32(Hex)编码。
	 * 
	 * @param data 待编码的数据。
	 * @param charset 字符集。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null或charset为空。
	 * @throws IllegalCharsetNameException 如果字符集名称不合法。
	 * @throws UnsupportedCharsetException 如果系统不支持charset对应的字符集。
	 */
	public static byte[] encode(String data, String charset){
		Assert.isNotBlank(charset, "字符集不能为空!");
		return encode(data.getBytes(Charset.forName(charset)));
	}
	
	/**
	 * 对数据进行Base32(Hex)编码。
	 * 
	 * @param data 待编码的数据。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static byte[] encode(String data){
		return encode(data.getBytes(SystemUtils.DEFAULT_CHARSET));
	}
	
	/**
	 * 对数据进行Base32(Hex)编码，以字符串形式返回。
	 * 
	 * @param data 待编码的数据。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static String encode2string(byte[] data){
		byte[] result = encode(data);
		return new String(result);
	}
	
	/**
	 * 对数据进行Base32(Hex)编码，以字符串形式返回。
	 * 
	 * @param data 待编码的数据。
	 * @param charset 字符集。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null或charset为空。
	 * @throws IllegalCharsetNameException 如果字符集名称不合法。
	 * @throws UnsupportedCharsetException 如果系统不支持charset对应的字符集。
	 */
	public static String encode2string(String data, String charset){
		byte[] result = encode(data,charset);
		return new String(result);
	}
	
	/**
	 * 对数据进行Base32(Hex)编码，以字符串形式返回。
	 * 
	 * @param data 待编码的数据。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static String encode2string(String data){
		byte[] result = encode(data);
		return new String(result);
	}
	
	/**
	 * 对数据进行Base32(Hex)解码。
	 * 
	 * @param data 待解码的数据。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static byte[] decode(byte[] data){
		Assert.notNull(data, "要进行解码的数据不能为null!");
		Base32Decoder decoder = new Base32Decoder(true);
		return decoder.decode(data);
	}
	
	/**
	 * 对数据进行Base32(Hex)解码。
	 * 
	 * @param data 待解码的数据。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static byte[] decode(String data){
		return decode(data.getBytes());
	}
	
	/**
	 * 对数据进行Base32(Hex)解码。以字符串形式返回。
	 * 
	 * @param data 待解码的数据。
	 * @param charset 字符集。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null或charset为空。
	 * @throws IllegalCharsetNameException 如果字符集名称不合法。
	 * @throws UnsupportedCharsetException 如果系统不支持charset对应的字符集。
	 */
	public static String decode2string(byte[] data, String charset){
		Assert.isNotBlank(charset, "字符集不能为空!");
		byte[] result = decode(data);
		return new String(result,Charset.forName(charset));
	}
	
	/**
	 * 对数据进行Base32(Hex)解码。以字符串形式返回。
	 * 
	 * @param data 待解码的数据。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static String decode2string(byte[] data){
		byte[] result = decode(data);
		return new String(result,SystemUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * 对数据进行Base32(Hex)解码。以字符串形式返回。
	 * 
	 * @param data 待解码的数据。
	 * @param charset 字符集。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null或charset为空。
	 * @throws IllegalCharsetNameException 如果字符集名称不合法。
	 * @throws UnsupportedCharsetException 如果系统不支持charset对应的字符集。
	 */
	public static String decode2string(String data, String charset){
		Assert.isNotBlank(charset, "字符集不能为空!");
		byte[] result = decode(data);
		return new String(result,Charset.forName(charset));
	}
	
	/**
	 * 对数据进行Base32(Hex)解码。以字符串形式返回。
	 * 
	 * @param data 待解码的数据。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static String decode2string(String data){
		byte[] result = decode(data);
		return new String(result,SystemUtils.DEFAULT_CHARSET);
	}

	private Base32HexUtils() {}
	
}
