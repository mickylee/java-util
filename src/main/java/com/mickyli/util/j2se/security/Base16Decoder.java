package com.mickyli.util.j2se.security;


/** 
 *   <B>说       明</B>:Base16解码类。
 *   <p>解码原理：解码为编码逆过程，参见{@link Base16Encoder}。
 *
 */
public class Base16Decoder extends BaseDecoder{
	
	/**
	 * 解码表。
	 */
	private static final byte[] DECODE_TABLE = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	//                                  0  1
		-1, -1, -1, -1, -1, -1, -1, -1, 0, 1,
	//  2  3  4  5  6  7  8  9              
		2, 3, 4, 5, 6, 7, 8, 9, -1, -1,
	//  54  55  56  57  65  A   B   C   D   E	                                
		-1, -1, -1, -1, -1, 10, 11, 12, 13, 14,
	//  F
		15
	};
	
	/**
	 * 解码表。
	 */
	private final byte[] dTable = DECODE_TABLE;
	
	/**
	 * 以2个字节为解码字节单元。
	 */
	private int decodeUnitLength = 2;
	
	/**
	 * 以1个字节为解码后字节单元。
	 */
	private int decodedUnitLength = 1;

	@Override
	protected void decodeUnit(byte[] data, int offset, ByteList byteList) {
		int diff = (offset + decodeUnitLength) - data.length;
		if(diff == 1){
			//要解码的数据不正确，直接返回。
			return;
		}else{
			byte d1 = data[offset++];
			byte d2 = data[offset++];
			if(!validateData(d1)){
				d1 = 0;
			}
			if(!validateData(d2)){
				d2 = 0;
			}
			/*
			 * 2 E
			 * 在解码表中找到相应数值。
			 * 2 14
			 * 转换为字节形式
			 * 00000010 00001110
			 * 去掉前面4个0，变成2组bits。
			 * 0010 1110
			 * 将2组bits合并成一个字节。
			 * 00101110
			 */
			byte b1 = dTable[d1];
			byte b2 = dTable[d2];
			byteList.add((byte)(((b1 << 4) & 0xf0) + (b2 & 0x0f)));
		}
		
	}

	@Override
	protected int getDecodeUnitLength() {
		return decodeUnitLength;
	}
	
	@Override
	protected int getDecodedUnitLength() {
		return decodedUnitLength;
	}
	
	/**
	 * 验证数据(字节)是否存在于解码表中。
	 * 
	 * @param data 待验证数据。
	 * @return
	 *      否存在于解码表中。
	 */
	private boolean validateData(byte data) {
		if(data < 0 || data >= dTable.length){
			return false;
		}
		return true;
	}
	
}
