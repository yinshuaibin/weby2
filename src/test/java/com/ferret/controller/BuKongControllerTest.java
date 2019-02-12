package com.ferret.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SplitMapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuKongControllerTest {

    @Autowired
    private BuKongController buKongController;

    @Test
    public void tes1(){
//        Map buKongByContorlType = buKongController.findBuKongByContorlType("18", 1, 100);
//        System.out.println(buKongByContorlType.get("totalNum"));
//        System.out.println(buKongByContorlType);
    }

    @Test
    public void test2(){
        Map  reqMap = new HashMap();
        reqMap.put("pageSize",1000);
        reqMap.put("pageNum",1);
       // reqMap.put("totalNum",0);
        List<Integer> aa = new ArrayList<>();
        aa.add(180);
        aa.add(190);
        reqMap.put("reason","测试");
        reqMap.put("status","");
        //reqMap.put("startTime","2018");
       reqMap.put("endTime","2019");
        reqMap.put("contorlTypeIds",aa);
        Map buKongList = buKongController.findBuKongList(reqMap);
        System.out.println(buKongList.get("resultList"));
        System.out.println(buKongList);
    }

    @Test
    public void test3(){
        Map  reqMap = new HashMap();

        List<String> aa = new ArrayList<>();
        aa.add("92ae68dd-f4e7-4a77-a6d6-7affb939ff14");
        aa.add("f4a6a774-4566-40e6-ad13-1ae979d502dd");
        reqMap.put("status","0");
        reqMap.put("businessids",aa);
        String s = buKongController.updateBuKongStatus(reqMap);
        System.out.println(s);
    }
}
