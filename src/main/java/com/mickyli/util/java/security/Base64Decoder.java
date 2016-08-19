package com.mickyli.util.java.security;


/** 
 *   <B>说       明</B>:Base64解码类。支持标准Base64解码和URL And FileName Safe方式的Base64解码。
 *   <p>解码原理：解码为编码逆过程，参见{@link Base64Encoder}。
 *
 */
public class Base64Decoder extends BaseDecoder{

	/**
	 * 解码表。
	 */
	private static final byte[] DECODE_TABLE = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	//                                      +       -       /   0   1   2
		-1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54,
	//  3   4   5   6   7   8   9                               A  B  C  D  E
		55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
	//  F  G  H  I  J  K   L   M   N   O   P   Q   R   S   T   U   V   W   X
		5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
	//  Y   Z                   _       a   b   c   d   e   f   g   h   i
		24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34,
	//  j   k   l   m   n   o   p   q   r   s   t   u   v   w   x   y   z
		35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51
	};
	
	/**
	 * 解码表。
	 */
	private final byte[] dTable = DECODE_TABLE;

	/**
	 * 以4个字节为解码字节单元。
	 */
	private int decodeUnitLength = 4;
	
	/**
	 * 以3个字节为解码后字节单元。
	 */
	private int decodedUnitLength = 3;

	@Override
	protected void decodeUnit(byte[] data, int offset, ByteList byteList) {
		int diff = (offset + decodeUnitLength) - data.length;
		if(diff == 3){
			//要解码的数据不正确，直接返回。
			return;
		}
		/* (M)77    (T)84    (I)73    (z)122
		 * 从解码表中找到相应数值。
		 *    12       19       8        51
		 * 二进制形式。
		 * 00001100 00010011 00001000 00110011
		 * 每个字节去掉前面2个0，变成4组6bits。
		 *   001100   010011   001000   110011
		 * 将这个4组6bits组成3个字节。
		 * 00110001 00110010 00110011
		 */
		byte d1 = data[offset++];
		byte d2 = data[offset++];
		//数据(字节)是否存在于编码表中，如果不存在，置为无效。
		if(!validateData(d1)){
			d1 = 0;
		}
		if(!validateData(d2)){
			d2 = 0;
		}
		byte b1,b2,b3,b4; b1 = b2 = b3 = b4 = -1;
		b1 = dTable[d1];
		b2 = dTable[d2];
		if(offset < data.length){
			byte d3 = data[offset++];
			if(d3 == pad){
				/* (M)77    (Q)81       pad       pad
				 * 从解码表中找到相应数值。
				 *    12       16       -1        -1
				 * 二进制形式。
				 * 00001100 00010000     -         -
				 * 每个字节去掉前面2个0，变成2组6bits。
				 *   001100   010000
				 * 将这个2组6bits组成1个字节，其余bits丢弃。
				 * 00110001 
				 */
				byteList.add((byte)(((b1 << 2) & 0xfc) + ((b2 >>> 4) & 0x03)));
			}else{
				if(!validateData(d3)){
					d3 = 0;
				}
				b3 = dTable[d3];
				/* (M)77    (T)84    (I)73       pad
				 * 从解码表中找到相应数值。
				 *    12       19       8        -1
				 * 二进制形式。
				 * 00001100 00010011 00001000     -
				 * 每个字节去掉前面2个0，变成3组6bits。
				 *   001100   010011   001000
				 * 将这个3组6bits组成2个字节，其余bits丢弃。
				 * 00110001 00110010
				 */
				byteList.add((byte)(((b1 << 2) & 0xfc) + ((b2 >>> 4) & 0x03)));
				byteList.add((byte)(((b2 << 4) & 0xf0) + ((b3 >>> 2) & 0x0f)));
			}
			
			if(offset < data.length){
				byte d4 = data[offset++];
				if(d4 != pad){
					if(!validateData(d4)){
						d4 = 0;
					}
					b4 = dTable[d4];
					byteList.add((byte)(((b3 << 6) & 0xc0) + (b4 & 0x3f)));
				}
			}
		}else{
			/* (M)77    (Q)81       pad       pad
			 * 从解码表中找到相应数值。
			 *    12       16       -1        -1
			 * 二进制形式。
			 * 00001100 00010000     -         -
			 * 每个字节去掉前面2个0，变成2组6bits。
			 *   001100   010000
			 * 将这个2组6bits组成1个字节，其余bits丢弃。
			 * 00110001 
			 */
			byteList.add((byte)(((b1 << 2) & 0xfc) + ((b2 >>> 4) & 0x03)));
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
