package com.mickyli.util.test.security; 

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** 
 *   <B>说       明</B>:UtilSuit-安全工具类测试集。
 *   
 *   <p>运行util.security包下所有单元测试。
 *   in eclipse:
 *   Run As -> Junit test
 *   or
 *   Debug As -> Junit test
 *   
 */
@RunWith(Suite.class)
@SuiteClasses(
		{
			//信息摘要工具测试类
			MessageDigestUtilsTest.class,
			//十六进制工具测试类
			HexUtilsTest.class,
			//MD5工具测试类
			MD5UtilsTest.class,
			//CRC校验工具测试类
			CRCUtilsTest.class,
			//Base16编码解码测试类
			Base16Test.class,
			//Base16工具测试类
			Base16UtilsTest.class,
			//Base32编码解码测试类
			Base32Test.class,
			//标准Base32工具测试类
			Base32UtilsTest.class,
			//标准Base32(Hex)工具测试类
			Base32HexUtilsTest.class,
			//Base64编码解码测试类
			Base64Test.class,
			//标准Base64工具测试类
			Base64UtilsTest.class,
			//Base64(URL And FileName Safe)工具测试类
			Base64UFSUtilsTest.class,
			//DES工具测试类
			DESUtilsTest.class,
			//DESede工具测试类
			DESedeUtilsTest.class,
			//AES工具测试类
			AESUtilsTest.class,
			//PBE工具测试类
			PBEUtilsTest.class,
			//DH工具测试类
			DHUtilsTest.class,
			//RSA工具测试类
			RSAUtilsTest.class,
			//RSA数字签名工具测试类
			RSASignatureUtilsTest.class,
			//DSA数字签名工具测试类
			DSASignatureUtilsTest.class,
		}
)
public final class UtilSuit {

	private UtilSuit(){}

}
