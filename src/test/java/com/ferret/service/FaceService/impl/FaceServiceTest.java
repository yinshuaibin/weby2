package com.ferret.service.FaceService.impl;


import com.ferret.bean.InterfaceBean.FaceExtraction;
import com.ferret.service.face.FaceService;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


/**
 * y 1224
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class FaceServiceTest {

    @Autowired
    private FaceService faceService;

    @Test
    public void testOneToOne(){
        String base1 = ImageBase64Utils.getImageStr("D:\\eclipse\\111.jpg");
        String base2 = ImageBase64Utils.getImageStr("D:\\eclipse\\111.jpg");
        float i = faceService.faceCompareInterface(base1, base2);
        System.out.println(i);
    }

    @Test
    public void testFeature(){
        String base1 = ImageBase64Utils.getImageStr("D:\\eclipse\\111.jpg");
        Object o = faceService.FaceExtraction(base1);
        FaceExtraction f= (FaceExtraction)o;
        String str = new String(Base64.getDecoder().decode(f.getFeature_List().get(0).getFeature()));
        System.out.println(str);
        System.out.println(o);
    }
}
