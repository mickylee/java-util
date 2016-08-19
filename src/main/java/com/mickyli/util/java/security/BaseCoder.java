package com.mickyli.util.java.security;
/** 
 *   <B>说       明</B>:Base编码解码接口。
 *
 */
interface BaseCoder {

	/**
	 * 根据MIME规范，每行字符的长度限制为76个字符。
	 */
	public static final int DEFAULT_CHAR_LINE_LIMIT = 76;
	
	/**
	 * 默认填充字符。
	 */
	public static final byte DEFAULT_PAD = '=';
	
	/**
	 * 空字节数组。
	 */
	public static final byte[] EMPTY_BYTES = new byte[0];
	
	/**
	 * 设置填充字符。
	 * 
	 * @param pad 填充字符。
	 */
	public void setPad(byte pad);
	
}
