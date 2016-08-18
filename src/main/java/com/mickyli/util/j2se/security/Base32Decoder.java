package com.mickyli.util.j2se.security;


/** 
 *   <B>说       明</B>:Base32解码类。包括标准Base32解码和Extended Hex Base32解码。
 *   <p>解码原理：解码为编码逆过程，参见{@link Base32Encoder}。
 */
public class Base32Decoder extends BaseDecoder{
	
	/**
	 * 标准解码表。
	 */
	private static final byte[] STANDARD_DECODE_TABLE = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	//  2   3   4   5   6   7
		26, 27, 28, 29, 30, 31, -1, -1, -1, -1,
	//                      A  B  C  D  E
		-1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
	//  F  G  H  I  J  K   L   M   N   O
		5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	//  P   Q   R   S   T   U   V   W   X   Y
		15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
	//  Z
		25
	};
	
	/**
	 * HEX解码表。
	 */
	private static final byte[] HEX_DECODE_TABLE = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	//                                  0  1
		-1, -1, -1, -1, -1, -1, -1, -1, 0, 1,
	//  2  3  4  5  6  7  8  9
		2, 3, 4, 5, 6, 7, 8, 9, -1, -1,
	//                      A   B   C   D   E
		-1, -1, -1, -1, -1, 10, 11, 12, 13, 14,
	//  F   G   H   I   J   K   L   M   N   O
		15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
	//  P   Q   R   S   T   U   V
		25, 26, 27, 28, 29, 30, 31
	};
	
	/**
	 * 以8个字节为解码字节单元。
	 */
	private int decodeUnitLength = 8;
	
	/**
	 * 以5个字节为解码后字节单元。
	 */
	private int decodedUnitLength = 5;
	
	private final byte[] dTable;
	
	public Base32Decoder() {
		this(false);
	}
	
	/**
	 * @param useHex 是否使用Extended Hex方式解码。
	 */
	public Base32Decoder(boolean useHex) {
		super();
		if(useHex){
			this.dTable = HEX_DECODE_TABLE;
		}else{
			this.dTable = STANDARD_DECODE_TABLE;
		}
	}

	@Override
	protected void decodeUnit(byte[] data, int offset, ByteList byteList) {
		int diff = (offset + decodeUnitLength) - data.length;
		if(diff > 0){
			//要解码的数据不正确，直接返回。
			return;
		}else{
			byte d1 = data[offset++];
			byte d2 = data[offset++];
			byte d3 = data[offset++];
			byte d4 = data[offset++];
			byte d5 = data[offset++];
			byte d6 = data[offset++];
			byte d7 = data[offset++];
			byte d8 = data[offset++];
			//数据(字节)是否存在于编码表中，如果不存在，置为无效。
			if(!validateData(d1)){
				d1 = 0;
			}
			if(!validateData(d2)){
				d2 = 0;
			}
			if(d3 == pad){
				if(d4 == pad && d5 == pad && d6 == pad && d7 == pad && d8 == pad){
					/*
					 * G E pad pad pad pad pad pad
					 * 在解码表中找到对应数值。
					 * 00000110 00000100
					 * 每个字节前面去掉3个0，变成2组bits。
					 *    00110    00100
					 * 用第一组bits和第二组bits的前3位组成一个字节。
					 * 00110001
					 */
					byte b1 = dTable[d1];
					byte b2 = dTable[d2];
					byteList.add((byte)(((b1 << 3) & 0xf8) + ((b2 >>> 2) & 0x07))); 
				}else{
					//要解码的数据不正确，直接返回。
					return;
				}
			}else{
				if(!validateData(d3)){
					d3 = 0;
				}
				if(!validateData(d4)){
					d4 = 0;
				}
				if(d5 == pad){
					if(d6 == pad && d7 == pad && d8 == pad){
						/*
						 * G E Z A pad pad pad pad
						 * 在解码表中找到对应数值。
						 * 00000110 00000100 00011001 00000000
						 * 每个字节前面去掉3个0，变成4组bits。
						 *    00110    00100    11001    00000
						 * 组成2个字节，剩余的bit丢弃。
						 * 00110001 00110010
						 */
						byte b1 = dTable[d1];
						byte b2 = dTable[d2];
						byte b3 = dTable[d3];
						byte b4 = dTable[d4];
						byteList.add((byte)(((b1 << 3) & 0xf8) + ((b2 >>> 2) & 0x07)));
						byteList.add((byte)(((b2 << 6) & 0xc0) + ((b3 << 1) & 0x3e) + ((b4 >>> 4) & 0x01)));
					}else{
						//要解码的数据不正确，直接返回。
						return;
					}
				}else{
					if(!validateData(d5)){
						d5 = 0;
					}
					if(d6 == pad){
						if(d7 == pad && d8 == pad){
							/*
							 * G E Z D G pad pad pad
							 * 在解码表中找到对应数值。
							 * 00000110 00000100 00011001 00000011 00000110
							 * 每个字节前面去掉3个0，变成5组bits。
							 *    00110    00100    11001    00011    00110
							 * 组成3个字节，剩余的bit丢弃。
							 * 00110001 00110010 00110011
							 */
							byte b1 = dTable[d1];
							byte b2 = dTable[d2];
							byte b3 = dTable[d3];
							byte b4 = dTable[d4];
							byte b5 = dTable[d5];
							byteList.add((byte)(((b1 << 3) & 0xf8) + ((b2 >>> 2) & 0x07)));
							byteList.add((byte)(((b2 << 6) & 0xc0) + ((b3 << 1) & 0x3e) + ((b4 >>> 4) & 0x01)));
							byteList.add((byte)(((b4 << 4) & 0xf0) + ((b5 >>> 1) & 0x0f)));
						}else{
							//要解码的数据不正确，直接返回。
							return;
						}
					}else{
						if(!validateData(d6)){
							d6 = 0;
						}
						if(!validateData(d7)){
							d7 = 0;
						}
						if(d8 == pad){
							/*
							 * G E Z D H E Q pad
							 * 在解码表中找到对应数值。
							 * 00000110 00000100 00011001 00000011 00000111 00000100 00010000
							 * 每个字节前面去掉3个0，变成7组bits。
							 *    00110    00100    11001    00011    00111    00100    10000
							 * 组成4个字节，剩余的bit丢弃。
							 * 00110001 00110010 00110011 10010010
							 */
							byte b1 = dTable[d1];
							byte b2 = dTable[d2];
							byte b3 = dTable[d3];
							byte b4 = dTable[d4];
							byte b5 = dTable[d5];
							byte b6 = dTable[d6];
							byte b7 = dTable[d7];
							byteList.add((byte)(((b1 << 3) & 0xf8) + ((b2 >>> 2) & 0x07)));
							byteList.add((byte)(((b2 << 6) & 0xc0) + ((b3 << 1) & 0x3e) + ((b4 >>> 4) & 0x01)));
							byteList.add((byte)(((b4 << 4) & 0xf0) + ((b5 >>> 1) & 0x0f)));
							byteList.add((byte)(((b5 << 7) & 0x80) + ((b6 << 2) & 0x7c) + ((b7 >>> 3) & 0x03)));
						}else{
							/*
							 * G E Z D H E S F
							 * 在解码表中找到对应数值。
							 * 00000110 00000100 00011001 00000011 00000111 00000100 00010010 00001111
							 * 每个字节前面去掉3个0，变成8组bits。
							 *    00110    00100    11001    00011    00111    00100    10010    01111
							 * 组成5个字节，剩余的bit丢弃。
							 * 00110001 00110010 00110011 10010010 01001111
							 */
							byte b1 = dTable[d1];
							byte b2 = dTable[d2];
							byte b3 = dTable[d3];
							byte b4 = dTable[d4];
							byte b5 = dTable[d5];
							byte b6 = dTable[d6];
							byte b7 = dTable[d7];
							byte b8 = dTable[d8];
							byteList.add((byte)(((b1 << 3) & 0xf8) + ((b2 >>> 2) & 0x07)));
							byteList.add((byte)(((b2 << 6) & 0xc0) + ((b3 << 1) & 0x3e) + ((b4 >>> 4) & 0x01)));
							byteList.add((byte)(((b4 << 4) & 0xf0) + ((b5 >>> 1) & 0x0f)));
							byteList.add((byte)(((b5 << 7) & 0x80) + ((b6 << 2) & 0x7c) + ((b7 >>> 3) & 0x03)));
							byteList.add((byte)(((b7 << 5) & 0xe0) + (b8 & 0x1f)));
						}
					}
				}
			}
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
