package com.mickyli.util.j2se;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Java常用数据类型枚举
 */
public enum Types {
	
	INT(Integer.class), 
	SHORT(Short.class), 
	LONG(Long.class), 
	CHAR(Character.class), 
	STRING(String.class), 
	BYTE(Byte.class), 
	FLOAT(Float.class), 
	DOUBLE(Double.class), 
	DATE(Date.class), 
	BIGDECIMAL(BigDecimal.class);

	private Class<?> clazz;

	Types(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getValue() {
		return clazz;
	}

}
