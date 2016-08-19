package com.mickyli.util.java.security; 

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/** 
 *   <B>说       明</B>:循环冗余校验工具类
 *
 */
public class CRCUtils {

	/**
	 * 计算并获取数据的CRC32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      长整型的CRC32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static long getCRC32Value(byte[] input){
		return getCheckValue(input, new CRC32());
	}

	/**
	 * 计算并获取数据的CRC32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      长整型的CRC32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static long getCRC32Value(String input){
		Assert.notNull(input, "要进行校验码计算的数据不能为空!");
		return getCheckValue(input.getBytes(SystemUtils.DEFAULT_CHARSET), new CRC32());
	}

	/**
	 * 计算并获取数据的CRC32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      字符串型的CRC32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static String getCRC32StringValue(byte[] input){
		long crc32 = getCRC32Value(input);
		return Long.toHexString(crc32);
	}

	/**
	 * 计算并获取数据的CRC32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      字符串型的CRC32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static String getCRC32StringValue(String input){
		Assert.notNull(input, "要进行校验码计算的数据不能为空!");
		return getCRC32StringValue(input.getBytes(SystemUtils.DEFAULT_CHARSET));
	}

	/**
	 * 计算并获取数据的CRC32校验码
	 * 
	 * @param inputStream 进行校验码计算的数据流
	 * @return
	 *      长整型的CRC32校验码
	 * @throws IOException 如果从数据流中读数据发生错误
	 * @throws IllegalArgumentException 如果inputStream为null。
	 */
	public static long getCRC32Value(InputStream inputStream) throws IOException{
		return getCheckValue(inputStream, new CRC32());
	}

	/**
	 * 计算并获取数据的CRC32校验码
	 * 
	 * @param inputStream 进行校验码计算的数据
	 * @return
	 *      字符串型的CRC32校验码
	 * @throws IOException 如果从数据流中读数据发生错误
	 * @throws IllegalArgumentException 如果inputStream为null。
	 */
	public static String getCRC32StringValue(InputStream inputStream) throws IOException{
		long crc32 = getCRC32Value(inputStream);
		return Long.toHexString(crc32);
	}

	/**
	 * 计算并获取数据的Adler32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      长整型的Adler32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static long getAdler32Value(byte[] input){
		return getCheckValue(input, new Adler32());
	}

	/**
	 * 计算并获取数据的Adler32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      长整型的Adler32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static long getAdler32Value(String input){
		Assert.notNull(input, "要进行校验码计算的数据不能为空!");
		return getCheckValue(input.getBytes(SystemUtils.DEFAULT_CHARSET), new Adler32());
	}

	/**
	 * 计算并获取数据的Adler32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      字符串型的Adler32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static String getAdler32StringValue(byte[] input){
		long adler32 = getAdler32Value(input);
		return Long.toHexString(adler32);
	}

	/**
	 * 计算并获取数据的Adler32校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @return
	 *      字符串型的Adler32校验码
	 * @throws IllegalArgumentException 如果input为null。
	 */
	public static String getAdler32StringValue(String input){
		Assert.notNull(input, "要进行校验码计算的数据不能为空!");
		return getAdler32StringValue(input.getBytes(SystemUtils.DEFAULT_CHARSET));
	}

	/**
	 * 计算并获取数据的Adler32校验码
	 * 
	 * @param inputStream 进行校验码计算的数据流
	 * @return
	 *      长整型的Adler32校验码
	 * @throws IOException 如果从数据流中读数据发生错误
	 * @throws IllegalArgumentException 如果inputStream为null。
	 */
	public static long getAdler32Value(InputStream inputStream) throws IOException{
		return getCheckValue(inputStream, new Adler32());
	}

	/**
	 * 计算并获取数据的Adler32校验码
	 * 
	 * @param inputStream 进行校验码计算的数据
	 * @return
	 *      字符串型的Adler32校验码
	 * @throws IOException 如果从数据流中读数据发生错误
	 * @throws IllegalArgumentException 如果inputStream为null。
	 */
	public static String getAdler32StringValue(InputStream inputStream) throws IOException{
		long adler32 = getAdler32Value(inputStream);
		return Long.toHexString(adler32);
	}

	/**
	 * 计算并获取数据的校验码
	 * 
	 * @param input 进行校验码计算的数据
	 * @param checksum 校验类
	 * @return
	 *      长整型的校验码
	 * @throws IllegalArgumentException 如果input为null，或者checksum为null。
	 */
	private static long getCheckValue(byte[] input, Checksum checksum){
		Assert.notNull(input, "进行校验码计算的数据不能为空!");
		Assert.notNull(checksum, "校验类不能为空!");
		checksum.update(input, 0, input.length);
		return checksum.getValue();
	}

	/**
	 * 计算并获取数据的校验码
	 * 
	 * @param inputStream 进行校验码计算的数据流
	 * @param checksum 校验类
	 * @return
	 *      长整型的校验码
	 * @throws IOException 如果从数据流中读数据发生错误
	 * @throws IllegalArgumentException 如果inputStream为null，或者checksum为null。
	 */
	private static long getCheckValue(InputStream inputStream, Checksum checksum) throws IOException{
		Assert.notNull(inputStream, "进行校验码计算的数据不能为空!");
		Assert.notNull(checksum, "校验类不能为空!");
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = inputStream.read(buffer)) > -1){
			checksum.update(buffer, 0, len);
		}
		return checksum.getValue();
	}

	private CRCUtils() {}

}
