package com.mickyli.util.algorithmImpl;

import java.io.File;

import org.junit.Test;

import com.mickyli.util.java.file.FileAlgorithmUtil;

import test.TestUtil;

public class FileImplTest {

    @Test
    public void TestCpdetector() throws Exception {
        System.out.println(FileAlgorithmUtil.simpleEncoding(TestUtil.path + "GBK.txt"));
        System.out.println(FileAlgorithmUtil.cpdetector((new File(TestUtil.path + "GBK.txt")).toURL()));
        System.out.println(new FileAlgorithmUtil().guestFileEncoding(TestUtil.path + "GBK.txt"));
        System.out.println(new FileAlgorithmUtil().guestFileEncoding(TestUtil.path + "GBK.txt", 3));


        System.out.println(FileAlgorithmUtil.simpleEncoding(TestUtil.path + "UTF8.txt"));
        System.out.println(FileAlgorithmUtil.cpdetector((new File(TestUtil.path + "UTF8.txt")).toURL()));
        System.out.println(new FileAlgorithmUtil().guestFileEncoding(TestUtil.path + "UTF8.txt"));
        System.out.println(new FileAlgorithmUtil().guestFileEncoding(TestUtil.path + "UTF8.txt", 3));
    }
}