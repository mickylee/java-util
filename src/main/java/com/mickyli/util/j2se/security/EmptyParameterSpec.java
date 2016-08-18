package com.mickyli.util.j2se.security;

import java.security.spec.AlgorithmParameterSpec;

/** 
 *   <B>说       明</B>:空算法参数材料，用于一些方法的占位。
 *
 */
class EmptyParameterSpec implements AlgorithmParameterSpec{

	private static final EmptyParameterSpec ONE = new EmptyParameterSpec();
	
	public static EmptyParameterSpec getInstance(){
		return ONE;
	}
	
	private EmptyParameterSpec(){}
	
}
