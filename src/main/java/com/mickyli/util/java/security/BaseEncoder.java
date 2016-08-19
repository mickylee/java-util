package com.mickyli.util.java.security;

import static com.mickyli.util.java.security.SystemUtils.LINESEPARATOR;

/** 
 *   <B>说       明</B>:Base编码基类。
 */
abstract class BaseEncoder implements BaseCoder{
	
	/**
	 * 换行符(字节)。
	 */
	private static final byte[] LS_BYTES = LINESEPARATOR.getBytes();
	
	/**
	 * 填充字符。
	 */
	protected byte pad = DEFAULT_PAD;
	
	/**
	 * 对数据进行编码。
	 * 
	 * @param data 待编码的数据。
	 * @return
	 *      编码后的数据。
	 * @throws IllegalArgumentException 如果data为null。 
	 */
	public byte[] encode(byte[] data){
		Assert.notNull(data, "要进行编码的数据不能为null!");
		int dataLen = data.length;
		if(dataLen == 0){
			return EMPTY_BYTES;
		}
		int encodeUnitLength = getEncodeUnitLength();
		int byteLineLength = DEFAULT_CHAR_LINE_LIMIT / getEncodedUnitLength() * getEncodeUnitLength();
		//创建一个字节列表。
		final ByteList byteList = new ByteList(dataLen);
		for(int i = 0; i < dataLen;){
			encodeUnit(data, i, byteList);
			i += encodeUnitLength;
			//写入换行符。
			if(i > 0 
				&& i <= dataLen 
				&& i % byteLineLength == 0){
				for(int j = 0; j < LS_BYTES.length; j++){
					byteList.add(LS_BYTES[j]);
				}
			}
		}
		return byteList.getBytes();
	}

	/**
	 * 对数据单元进行编码。
	 * 
	 * @param data 待编码数据。
	 * @param offset 偏移量。
	 * @param byteList 字节列表。(存放编码后的字节)
	 */
	protected abstract void encodeUnit(byte[] data, int offset, ByteList byteList);

	/**
	 * 获取编码单元长度(字节)。
	 * 
	 * @return
	 *      编码单元长度。
	 */
	protected abstract int getEncodeUnitLength();
	
	/**
	 * 获取编码后单元长度(字节)。
	 * 
	 * @return
	 *      编码后单元长度。
	 */
	protected abstract int getEncodedUnitLength();
	
	/**
	 * 设置填充字符。
	 * 
	 * @param pad 填充字符。
	 */
	public void setPad(byte pad) {
		this.pad = pad;
	}
	
}
