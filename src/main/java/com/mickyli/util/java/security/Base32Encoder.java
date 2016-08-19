package com.mickyli.util.java.security;


/** 
 *   <B>说       明</B>:Base32编码类。包括标准Base32编码和Extended Hex Base32编码。
 *   <p>参考RFC 4648。
 *   <p>Base32编码原理：将字节单元(40个比特位)分为8组，每组5个比特位。然后将每组前面补3个0，形成一个新字节。
 *   这样相当于5个字节变成了8个字节。然后通过每个字节的数值到编码表中找到相应的字符，完成编码工作。
 *   <p>当待编码数据中出现不够一个字节单元(40位)的情况，比如1个、2、3个和4个字节。那么分组时在末尾补0，并且在编码时
 *   在末尾进行填充(一个字节单元要编码成8个字符，不够的要进行填充)，默认填充字符为'='。
 *
 */
public class Base32Encoder extends BaseEncoder{
	
	/**
	 * 标准Base32编码字符表，参考RFC 4648。
	 */
	private static final byte[] STANDARD_ENCODE_TABLE = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', '2', '3', '4', '5', '6', '7'
	};
	
	/**
	 * Extended Hex Base32编码字符表，参考RFC 4648。
	 */
	private static final byte[] HEX_ENCODE_TABLE = {
		'0', '1', '2', '3', '4', '5', '6', '7', 
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 
		'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
	};
	
	/**
	 * 编码表。
	 */
	private final byte[] eTable;
	
	public Base32Encoder() {
		this(false);
	}
	
	/**
	 * @param useHex 是否使用Extended Hex方式编码。
	 */
	public Base32Encoder(boolean useHex){
		super();
		if(useHex){
			this.eTable = HEX_ENCODE_TABLE;
		}else{
			this.eTable = STANDARD_ENCODE_TABLE;
		}
	}
	
	/**
	 * 以5个字节为编码字节单元。
	 */
	private int encodeUnitLength = 5;
	
	/**
	 * 以8个字节为编码字节单元。
	 */
	private int encodedUnitLength = 8;

	@Override
	protected void encodeUnit(byte[] data, int offset, ByteList byteList) {
		int dataLength = data.length;
		int endIndex = offset + encodeUnitLength;
		if(dataLength < endIndex){
			//字节单元组不够3个的情况。
			int diff = endIndex - dataLength;
			if(diff == 1){
				/*
				 *   00110001 00110010 00110011 10010010
				 * 将字节单元分为7份，不够的末尾补0。
				 *   00110|00100|11001|00011|00111|00100|10000
				 * 在每份bits前面补3个0，变成7个字节。
				 * 00000110 00000100 00011001 00000011 00000111 00000100 00010000 pad
				 * 在字符表中找到对应字符。
				 */
				byte b1 = data[offset++];
				byte b2 = data[offset++];
				byte b3 = data[offset++];
				byte b4 = data[offset++];
				byteList.add(eTable[(b1 >>> 3) & 0x1f]);
				byteList.add(eTable[((b1 << 2) & 0x1c) + ((b2 >>> 6) & 0x03)]);
				byteList.add(eTable[((b2 >>> 1) & 0x1f)]);
				byteList.add(eTable[((b2 << 4) & 0x10) + ((b3 >>> 4) & 0x0f)]);
				byteList.add(eTable[((b3 << 1) & 0x1e) + ((b4 >>> 7) & 0x01)]);
				byteList.add(eTable[(b4 >>> 2) & 0x1f]);
				byteList.add(eTable[(b4 << 3) & 0x18]);
				byteList.add(pad);
			}else if(diff == 2){
				/*
				 *   00110001 00110010 00110011
				 * 将字节单元分为5份，不够的末尾补0。
				 *   00110|00100|11001|00011|00110
				 * 在每份bits前面补3个0，变成5个字节。
				 * 00000110 00000100 00011001 00000011 00000110 pad pad pad
				 * 在字符表中找到对应字符。
				 */
				byte b1 = data[offset++];
				byte b2 = data[offset++];
				byte b3 = data[offset++];
				byteList.add(eTable[(b1 >>> 3) & 0x1f]);
				byteList.add(eTable[((b1 << 2) & 0x1c) + ((b2 >>> 6) & 0x03)]);
				byteList.add(eTable[((b2 >>> 1) & 0x1f)]);
				byteList.add(eTable[((b2 << 4) & 0x10) + ((b3 >>> 4) & 0x0f)]);
				byteList.add(eTable[(b3 << 1) & 0x1e]);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
			}else if(diff == 3){
				/*
				 *   00110001 00110010
				 * 将字节单元分为4份，不够的末尾补0。
				 *   00110|00100|11001|00000
				 * 在每份bits前面补3个0，变成4个字节。
				 * 00000110 00000100 00011001 00000000 pad pad pad pad
				 * 在字符表中找到对应字符。
				 */
				byte b1 = data[offset++];
				byte b2 = data[offset++];
				byteList.add(eTable[(b1 >>> 3) & 0x1f]);
				byteList.add(eTable[((b1 << 2) & 0x1c) + ((b2 >>> 6) & 0x03)]);
				byteList.add(eTable[((b2 >>> 1) & 0x1f)]);
				byteList.add(eTable[(b2 << 4) & 0x10]);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
			}else if(diff == 4){
				/*
				 *   00110001
				 * 将字节单元分为2份，不够的末尾补0。
				 *   00110|00100
				 * 在每份bits前面补3个0，变成2个字节。
				 * 00000110 00000100 pad pad pad pad pad pad
				 * 在字符表中找到对应字符。
				 */
				byte b1 = data[offset++];
				byteList.add(eTable[(b1 >>> 3) & 0x1f]);
				byteList.add(eTable[(b1 << 2) & 0x1c]);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
				byteList.add(pad);
			}else{
				//never here
			}
		}else{
			/*
			 *   00110001 00110010 00110011 10010010 01001111 
			 * 将字节单元(5个字节)40bits分为8份。
			 *   00110|00100|11001|00011|00111|00100|10010|01111
			 * 在每份bits前面补3个0，变成8个字节。
			 * 00000110 00000100 00011001 00000011 00000111 00000100 00010010 00001111
			 * 在字符表中找到对应字符。
			 */
			byte b1 = data[offset++];
			byte b2 = data[offset++];
			byte b3 = data[offset++];
			byte b4 = data[offset++];
			byte b5 = data[offset++];
			byteList.add(eTable[(b1 >>> 3) & 0x1f]);
			byteList.add(eTable[((b1 << 2) & 0x1c) + ((b2 >>> 6) & 0x03)]);
			byteList.add(eTable[((b2 >>> 1) & 0x1f)]);
			byteList.add(eTable[((b2 << 4) & 0x10) + ((b3 >>> 4) & 0x0f)]);
			byteList.add(eTable[((b3 << 1) & 0x1e) + ((b4 >>> 7) & 0x01)]);
			byteList.add(eTable[(b4 >>> 2) & 0x1f]);
			byteList.add(eTable[((b4 << 3) & 0x18) + ((b5 >>> 5) & 0x07)]);
			byteList.add(eTable[b5 & 0x1f]);
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
