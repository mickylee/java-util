package com.mickyli.util.java.project;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Properties文件的读写
 */
public class PropertiesUtils {
	
	/**
	 * 读取Properties配置文件内容
	 * @param filePath
	 * @return Properties
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Properties readProperties(String filePath) throws FileNotFoundException, IOException{
		Properties properties=new Properties();
		properties.load(new FileInputStream(new File(filePath)));
		return properties;
	}
	
	/**
	 * 写key-value到properties文件 相同的key会被覆盖 追加不同的key-value
	 * @param key 键
	 * @param value 值
	 * @param filePath 文件路径
	 * @param comment key-value的注释
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeProperties(String key,String value,String comment,String filePath) throws FileNotFoundException, IOException{
		Properties properties=new Properties();
		
		File file=new File(filePath);
		if(file.exists()){
			FileInputStream fis=new FileInputStream(file);
			properties.load(fis);
			fis.close();
		}
		properties.setProperty(key, value);
		properties.store(new FileOutputStream(new File(filePath)), comment);
	}

	/**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public static String key(String key) {
        return System.getProperty(key);
    }

    /**
     * 根据Key读取Value
     *
     * @param filePath 属性文件
     * @param key      需要读取的属性
     */
    public static String GetValueByKey(String filePath, String key) {
        Properties pps = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            pps.load(in);
            return pps.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Properties的全部信息
     *
     * @param filePath 读取的属性文件
     * @return 返回所有的属性 key:value<>key:value
     */
    public static String GetAllProperties(String filePath) throws IOException {
        Properties pps = new Properties();
        String     str = "";
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            pps.load(in);
            Enumeration en = pps.propertyNames();
            while (en.hasMoreElements()) {
                String strKey = (String) en.nextElement();
                String strValue = pps.getProperty(strKey);
                str += strKey + ":" + strValue + "<>";
            }
        }
        return str.substring(0, str.lastIndexOf("<>"));
    }

    /**
     * 写入Properties信息
     *
     * @param filePath 写入的属性文件
     * @param pKey     属性名称
     * @param pValue   属性值
     */
    public static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
        Properties props = new Properties();

        props.load(new FileInputStream(filePath));
        // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream fos = new FileOutputStream(filePath);
        props.setProperty(pKey, pValue);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        props.store(fos, "Update '" + pKey + "' value");

    }
}
