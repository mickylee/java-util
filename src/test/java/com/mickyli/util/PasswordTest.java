package com.mickyli.util;

import junit.framework.TestCase;

import com.mickyli.util.java.project.Password;

/**
 * 密码工具类相关的测试
 */
public class PasswordTest extends TestCase {
    public void testMd5() throws Exception {
        System.out.println(Password.md5("12345"));
    }

    public void testIsSec() throws Exception {
        System.out.println(Password.isSec("12345"));
        System.out.println(Password.isSec("12345aa**"));
        System.out.println(Password.isSec("12345Aa**"));
    }

}