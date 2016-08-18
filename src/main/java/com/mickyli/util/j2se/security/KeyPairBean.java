package com.mickyli.util.j2se.security;

import java.util.Arrays;

/** 
 *   <B>说       明</B>:密钥对Bean，表示一个密钥对，包括公钥和私钥。
 *
 */
public class KeyPairBean{

	/**
	 * 公钥。
	 */
	private byte[] publicKey;

	/**
	 * 私钥。
	 */
	private byte[] privateKey;

	public KeyPairBean(byte[] publicKey, byte[] privateKey) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(privateKey);
		result = prime * result + Arrays.hashCode(publicKey);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyPairBean other = (KeyPairBean) obj;
		if (!Arrays.equals(privateKey, other.privateKey))
			return false;
		if (!Arrays.equals(publicKey, other.publicKey))
			return false;
		return true;
	}

}
