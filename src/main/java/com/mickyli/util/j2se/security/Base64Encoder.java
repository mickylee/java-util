package com.mickyli.util.j2se.security;


/** 
 *   <B>说       明</B>:Base64编码类。支持标准Base64编码和URL And FileName Safe方式的Base64编码。
 *   <p>参考RFC 4648。
 *   <p>Base64编码原理：将字节单元(24个比特位)分为4组，每组6个比特位。然后将每组前面补两个0，形成一个新字节。
 *   这样相当于3个字节变成了4个字节。然后通过每个字节的数值到编码表中找到相应的字符，完成编码工作。
 *   <p>当待编码数据中出现不够一个字节单元(24位)的情况，比如1个或2个字节。那么分组时在末尾补0，并且在编码时
 *   在末尾进行填充(一个字节单元要编码成4个字符，不够的要进行填充)，默认填充字符为'='。
 *   <p>如果为URL And FileName Safe方式，不进行填充。
 *
 */
public class Base64Encoder extends BaseEncoder{
	
	/**
	 * 标准Base64编码字符表，参考RFC 4648。
	 */
	private static final byte[] STANDARD_ENCODE_TABLE = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};
	
	/**
	 * 应用于URL和FileName的编码字符表，参考RFC 4648。
	 */
	private static final byte[] URL_AND_FILENAME_SAFE_ENCODE_TABLE = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
	};
	
	/**
	 * 以3个字节为编码字节单元。
	 */
	private int encodeUnitLength = 3;
	
	/**
	 * 以3个字节为编码后字节单元。
	 */
	private int encodedUnitLength = 4;
	
	/**
	 * 编码表。
	 */
	private final byte[] eTable;
	
	/**
	 * 是否需要填充。
	 */
	private final boolean needPad;
	
	public Base64Encoder() {
		this(false);
	}
	
	/**
	 * @param isUrlAndFileNameSafe 是否应用于URL和文件名。
	 */
	public Base64Encoder(boolean isUrlAndFileNameSafe) {
		if(isUrlAndFileNameSafe){
			this.eTable = URL_AND_FILENAME_SAFE_ENCODE_TABLE;
			this.needPad = false;
		}else{
			this.eTable = STANDARD_ENCODE_TABLE;
			this.needPad = true;
		}
		
	}
	
	@Override
	protected void encodeUnit(byte[] data, int offset, ByteList byteList) {
		int dataLength = data.length;
		int endIndex = offset + encodeUnitLength;
		if(dataLength < endIndex){
			//字节单元组不够3个的情况。
			int diff = endIndex - dataLength;
			if(diff == 1){
				/*
				 * 字节单元组为2个的情况
				 *   00110001 00110010 
				 * 将字节单元分为3份。                  补0
				 *   001100|  010011|  0010 00
				 * 在每份bits前面补2个0，变成3个字节。
				 * 00001100 00010011 00001000 pad 
				 * 在字符表中找到对应字符。
				 */
				byte f = data[offset++];
				byte s = data[offset++];
				byteList.add(eTable[(f >>> 2) & 0x3f]);
				byteList.add(eTable[((f << 4) & 0x30) + ((s >>> 4) & 0x0f)]);
				byteList.add(eTable[(s << 2) & 0x3c]);
				if(needPad){
					byteList.add(pad);
				}
			}else if(diff == 2){
				/*
				 * 字节单元组为1个的情况
				 *   00110001
				 * 将字节单元分为2份。         补0        
				 *           001100|  010000  
				 * 在每份bits前面补2个0，变成2个字节。
				 * 00001100 00010000 pad pad
				 * 在字符表中找到对应字符。
				 */
				byte f = data[offset++];
				byteList.add(eTable[(f >>> 2) & 0x3f]);
				byteList.add(eTable[(f << 4) & 0x30]);
				if(needPad){
					byteList.add(pad);
					byteList.add(pad);
				}
			}else{
				//never here
			}
		}else{
			/*
			 *   00110001 00110010 00110011
			 * 将字节单元(3个字节)24bits分为4份。
			 *   001100|  010011|  001000|  110011
			 * 在每份bits前面补2个0，变成4个字节。
			 * 00001100 00010011 00001000 00110011
			 * 在字符表中找到对应字符。
			 */
			byte f = data[offset++];
			byte s = data[offset++];
			byte t = data[offset++];
			byteList.add(eTable[(f >>> 2) & 0x3f]);
			byteList.add(eTable[((f << 4) & 0x30) + ((s >>> 4) & 0x0f)]);
			byteList.add(eTable[((s << 2) & 0x3c) + ((t >>> 6) & 0x03)]);
			byteList.add(eTable[t & 0x3f]);
		}
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
