package com.mickyli.util.java.security;

import java.nio.charset.Charset;

/** 
 *   <B>说       明</B>:Base64工具类。
 */
public class Base16Utils {
	
	/**
	 * 对数据进行Base16编码。
	 * 
	 * @param data 待编码的数据。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static byte[] encode(byte[] data){
		Assert.notNull(data, "要进行编码的数据不能为null!");
		Base16Encoder encoder = new Base16Encoder();
		return encoder.encode(data);
	}
	
	/**
	 * 对数据进行Base16编码。
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
	 * 对数据进行Base16编码。
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
	 * 对数据进行Base16编码，以字符串形式返回。
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
	 * 对数据进行Base16编码，以字符串形式返回。
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
	 * 对数据进行Base16编码，以字符串形式返回。
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
	 * 对数据进行Base16解码。
	 * 
	 * @param data 待解码的数据。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static byte[] decode(byte[] data){
		Assert.notNull(data, "要进行解码的数据不能为null!");
		Base16Decoder decoder = new Base16Decoder();
		return decoder.decode(data);
	}
	
	/**
	 * 对数据进行Base16解码。
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
	 * 对数据进行Base16解码。以字符串形式返回。
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
	 * 对数据进行Base16解码。以字符串形式返回。
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
	 * 对数据进行Base16解码。以字符串形式返回。
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
	 * 对数据进行Base16解码。以字符串形式返回。
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

	private Base16Utils(){}
	
}
