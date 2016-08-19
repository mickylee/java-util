package com.mickyli.util.java.security; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mickyli.util.java.character.StringUtils;

/** 
 *   <B>说       明</B>:基于MD5算法的工具类
 *   <p>更多消息摘要算法支持参见 {@link MessageDigestUtils}
 *   
 *   @see MessageDigestUtils
 *
 */
public class MD5Utils {

	/**
	 * 对字符串数据进行MD5消息摘要。
	 * 
	 * @param input 进行MD5摘要的数据。
	 * @return
	 *      input的MD5摘要信息。
	 * @throws IllegalArgumentException 如果参数input为null或者JDK不支持MD5算法。
	 */
	public static String getMD5(String input){
		Assert.notNull(input, "进行MD5摘要的数据不能为空!");
		return MessageDigestUtils.getMD5StringDigest(input.getBytes(SystemUtils.DEFAULT_CHARSET));
	}

	/**
	 * 对数据进行MD5消息摘要。
	 * 
	 * @param input 进行MD5摘要的数据。
	 * @return
	 *      input的MD5摘要信息。
	 * @throws IllegalArgumentException 如果参数input为null或者JDK不支持MD5算法。
	 */
	public static String getMD5(byte[] input){
		return MessageDigestUtils.getMD5StringDigest(input);
	}
	
	/**
	 * 对输入流中的数据进行MD5消息摘要。
	 * 
	 * @param inputStream 进行MD5摘要的输入流。
	 * @return
	 *      inputStream中数据的MD5摘要。
	 * @throws IllegalArgumentException 如果参数inputStream为null或者JDK不支持MD5算法。
	 * @throws IllegalStateException 如果发生I/O异常。
	 */
	public static String getMD5(InputStream inputStream){
		try {
			return MessageDigestUtils.getMD5StringDigest(inputStream);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * 对文件进行MD5消息摘要。
	 * 
	 * @param file 进行MD5摘要的文件。
	 * @return
	 *      file的MD5摘要。
	 * @throws IllegalArgumentException 如果参数file为null或file对应的文件不存在或JDK不支持MD5算法。
	 * @throws IllegalStateException 如果发生I/O异常。
	 */
	public static String getMD5(File file){
		Assert.notNull(file, "进行MD5消息摘要的文件不能空!");
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			return getMD5(fileInputStream);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
	/**
	 * 获取字符串的MD5
	 * @param string 字符串
	 * @return String MD5字符串
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(String string,String salt) throws NoSuchAlgorithmException{
		
		String target=null;
		if(StringUtils.isBlank(salt)){
			target=string;
		}else{
			target=getMD5(salt+string+salt, null);
		}
		
		byte[] byteString=target.getBytes(Charset.forName("utf-8"));
		MessageDigest md=MessageDigest.getInstance("MD5");
		byte[] array=md.digest(byteString);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	    }
		return sb.toString();
	}
	
	/**
	 * 获取文件MD5
	 * @param filePath 文件路径
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws DigestException
	 */
	public static String getFileMD5(String filePath) throws NoSuchAlgorithmException, IOException, DigestException{
		MessageDigest md=MessageDigest.getInstance("MD5");
		int length=0;
		byte[] buffer=new byte[1024];
		byte[] array=null;
		InputStream is=new FileInputStream(new File(filePath));
		while((length=is.read(buffer))!=-1){
			md.update(buffer,0,length);
		}
		
		is.close();
		
		array=md.digest();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	    }
		
		return sb.toString();
	}
	private MD5Utils() {}

}
