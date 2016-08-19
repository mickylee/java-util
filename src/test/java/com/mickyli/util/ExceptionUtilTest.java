package com.mickyli.util;

import org.junit.Test;

import com.mickyli.util.java.project.ExceptionUtil;

/**
 * 测试异常工具类
 */
public class ExceptionUtilTest  {
    @Test
    public void testStackTraceToString() throws Exception {
        try{
            int i=1/0;
            System.out.println(i);
        }catch (Exception e){
            System.out.println(ExceptionUtil.stackTraceToString(e,"com.mickyli"));
        }
    }

}