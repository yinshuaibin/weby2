package com.ferret.controller;

import com.ferret.bean.ImageFace;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClusterControllerTest {

    @Autowired
    private ClusterController clusterController;

    @Test
    public void testGetFace(){
        Map reqMap = new HashMap();
        String base64  = ImageBase64Utils.getImageStr("D:\\eclipse\\1111.jpg");
        reqMap.put("baseImgeKey1",base64);
        ImageFace imgFace_num_new = clusterController.getImgFace_Num_new(reqMap);
        System.out.println(imgFace_num_new);
    }
}
