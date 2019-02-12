package com.ferret.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author cheng;
 * @date 2018/4/17;
 */
public class TestCopy {
    public static void main(String[] args) {
       ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            byte[] bytes1 = new byte[8];
            byte[] bytes2 = new byte[8];
            System.arraycopy(".ck".getBytes(CharEncoding.UTF_8),0,bytes1,0,".ck".getBytes(CharEncoding.UTF_8).length);
            System.arraycopy("v1.0".getBytes(CharEncoding.UTF_8),0,bytes2,0,".ck".getBytes(CharEncoding.UTF_8).length);
            byteBuffer.put(bytes1)
                    .put(bytes2)
                    .putInt(10)
                    .putInt(2048 * 10 + 1024);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       int i= byteBuffer.array().length;
    }
}
