package com.mickyli.util.j2se.security;


/** 
 *   <B>说       明</B>:Base16编码类。
 *   <p>Base16编码原理：将字节单元(8个比特位)分为2组，每组4个比特位。然后将每组前面补4个0，形成一个新字节。
 *   这样相当于1个字节变成了2个字节。然后通过每个字节的数值到编码表中找到相应的字符，完成编码工作。
 *   <p>由于不会出现分组后比特位不够的情况，所以不需要进行填充。
 *   
 */
public class Base16Encoder extends BaseEncoder{
	
	/**
	 * Base16编码字符表。
	 */
	private static final byte[] STANDARD_ENCODE_TABLE = {
		'0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};
	
	/**
	 * 以1个字节为编码字节单元。
	 */
	private int encodeUnitLength = 1;
	
	/**
	 * 以2个字节为编码后字节单元。
	 */
	private int encodedUnitLength = 2;
	
	/**
	 * 编码表。
	 */
	private final byte[] eTable = STANDARD_ENCODE_TABLE;
	
	public Base16Encoder() {
		super();
	}

	@Override
	protected void encodeUnit(byte[] data, int offset, ByteList byteList) {
		byte b = data[offset];
		/*
		 *   00110001
		 * 将字节单元(1个字节)8bits分为2份。
		 *   0011|0001
		 * 在每份bits前面补0，变成2个字节。
		 * 00000011 00000001
		 * 在字符表中找到对应字符。
		 */
		byteList.add(eTable[(b >>> 4) & 0x0f]);
		byteList.add(eTable[b & 0x0f]);
	}

	@Override
	protected int getEncodeUnitLength() {
		return encodeUnitLength;
	}

	@Override
	protected int getEncodedUnitLength() {
		return encodedUnitLength;
	}
	
}
