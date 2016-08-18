package com.mickyli.util.j2se.security;

import java.util.Arrays;

/** 
 *   <B>说       明</B>:字节列表。
 *   <p>可变长度的字节数组，只提供添加字节和获取字节数组的功能，供Base编码解码相关类使用。
 *
 */
class ByteList {

	/**
	 * 字节缓冲区。
	 */
	private byte[] buffer;

	/**
	 * 字节数量。
	 */
	private int count = 0;

	public ByteList() {
		this(32);
	}

	public ByteList(int size){
		if(size < 0){
			throw new IllegalArgumentException("ByteList的长度不能为负数!");
		}
		this.buffer = new byte[size];
	}

	/**
	 * 添加一个字节到字节列表。
	 * 
	 * @param b 要添加的字节。
	 */
	public void add(byte b){
		int newCount = count + 1;
		if (newCount > buffer.length) {
			int newSize = buffer.length << 1;
			buffer = Arrays.copyOf(buffer, Math.max(newSize, newCount));
		}
		buffer[count] = b;
		count = newCount;
	}

	/**
	 * 获取字节数组。
	 * 
	 * @return
	 *      字节数组。
	 */
	public byte[] getBytes(){
		return Arrays.copyOf(buffer, count);
	}

	/**
	 * 获取字节数量。
	 * 
	 * @return
	 */
	public int getByteCount() {
		return count;
	}
	
}
