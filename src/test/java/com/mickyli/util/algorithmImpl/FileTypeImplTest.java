package com.mickyli.util.algorithmImpl;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import test.TestUtil;

import com.mickyli.util.java.file.FileTypeUtil;

public class FileTypeImplTest extends TestCase {

    @Test
    public void testFileType() {
        assertEquals("gif", FileTypeUtil.getFileType(new File(TestUtil.path + "ali.gif")));
        assertEquals("png", FileTypeUtil.getFileType(new File(TestUtil.path + "tgepng")));
    }

}