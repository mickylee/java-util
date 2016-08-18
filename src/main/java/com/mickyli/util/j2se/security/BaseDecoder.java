package com.mickyli.util.j2se.security;

import static com.mickyli.util.j2se.security.SystemUtils.LINESEPARATOR;


/** 
 *   <B>说       明</B>:Base解码基类。
 *
 */
abstract class BaseDecoder implements BaseCoder{
	
	/**
	 * 换行符的字节个数。
	 */
	private final static int LS_BYTES_LENGTH = LINESEPARATOR.getBytes().length;
	
	/**
	 * 填充字符。
	 */
	protected byte pad = DEFAULT_PAD;
	
	/**
	 * 对数据进行解码。
	 * 
	 * @param data 待解码的数据。
	 * @return
	 *      解码后的数据。
	 * @throws IllegalArgumentException 如果data为null。 
	 */
	public byte[] decode(byte[] data){
		Assert.notNull(data, "要进行解码的数据不能为null!");
		int dataLen = data.length;
		if(dataLen == 0){
			return EMPTY_BYTES;
		}
		int decodeUnitLength = getDecodeUnitLength();
		//创建一个字节列表。
		final ByteList byteList = new ByteList(dataLen);
		int decodeLineLimit = DEFAULT_CHAR_LINE_LIMIT / getDecodeUnitLength() * getDecodedUnitLength();
		for(int i = 0; i < data.length; i += decodeUnitLength){
			decodeUnit(data, i, byteList);
			if(byteList.getByteCount() > 0 
					&& byteList.getByteCount() % decodeLineLimit == 0){
				i += LS_BYTES_LENGTH;
			}
		}
		return byteList.getBytes();
	}
	
	/**
	 * 对数据单元进行解码。
	 * 
	 * @param data 待解码的数据。
	 * @param offset 偏移量。
	 * @param byteList 字节列表。(存放解码后的字节)
	 */
	protected abstract void decodeUnit(byte[] data, int offset, ByteList byteList);

	/**
	 * 获取解码单元长度(字节)。
	 * 
	 * @return
	 *      解码单元长度。
	 */
	protected abstract int getDecodeUnitLength();
	
	/**
	 * 获取解码后单元长度(字节)。
	 * 
	 * @return
	 *      解码后单元长度。
	 */
	protected abstract int getDecodedUnitLength();
	
	/**
	 * 设置填充字符。
	 * 
	 * @param pad 填充字符。
	 */
	public void setPad(byte pad) {
		this.pad = pad;
	}
	
}
