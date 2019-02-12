package com.ferret.service.BuKongService.impl;


import com.ferret.bean.InterfaceBean.BuKongJH;
import com.ferret.service.bukong.BuKongService;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;


/**
 * y 1224
 *
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuKongServiceTest {

    @Autowired
    private BuKongService buKongService;

    @Test
    public void testAddBukong(){
        BuKongJH i = new BuKongJH();
        i.setCardnumber("410482199606177753");
        String base64  = ImageBase64Utils.getImageStr("D:\\eclipse\\111.jpg");
        i.setImageData(base64.replaceAll("\r|\n", ""));
        i.setName("yinshuaibin");
        i.setContorltype("1");
        i.setRemark("ceshiceshiceshiceshiceshiceshiceshi");
        i.setSex("1");
        i.setAddress("住址11111111111");
        i.setSimilar("0.09");
        i.setConage(18);
        i.setConreason("ceshi布控原因");
        buKongService.addOneBuKong(i);
    }

    @Test
    public void testAddBukongList(){
        List<BuKongJH> ss = new ArrayList<>();
        for(int x =0; x<10; x++){
            BuKongJH i = new BuKongJH();
            i.setCardnumber("410482199606177753");
            String base64  = ImageBase64Utils.getImageStr("D:\\eclipse\\111.jpg");
            i.setImageData(base64.replaceAll("\r|\n", ""));
            i.setName("测试名字2");
            i.setContorltype("99");
            i.setRemark("测试测试2");
            i.setConage(18);
            i.setContorltype("180");
            i.setConreason("ceshi布控原因");
            i.setSex("1");
            ss.add(i);
        }
        buKongService.addBuKongList(ss);
    }

    @Test
    public void testDeleteBUkong(){
        String s = buKongService.updateBukongStatus("5101d831-ef90-4612-a951-720ab1eb3ee0", "0");
        System.out.println(s);
    }

    @Test
    public void testUpdateBuKong(){
        BuKongJH i = new BuKongJH();
        i.setCardnumber("00000000");
        i.setBusinessid("f4ba0f3e-3a80-42fb-88c2-42d89afb546f");
        i.setName("12312312312312312312313");
        i.setContorltype("2");
        i.setRemark("c123123123123123123123");
        i.setSex("0");
        i.setConage(1888);
        i.setAddress("住址222222222222222");
        i.setSimilar("0.9");
        buKongService.uptadeBuKong(i);
    }

    @Test
    public void testFind(){
        List<BuKongJH> buKongByContorType = buKongService.findBuKongByContorlType("18", 100, 1);
        System.out.println(buKongByContorType.size());

        System.out.println(buKongService.findBuKongByContorlTypeCount("18"));
    }

    @Test
    public void testDEl(){
        List<String> ss = new ArrayList<>();
        ss.add("455");
        ss.add("457");
        String s = buKongService.delBuKongByBusinessid("e5f5f14d-ce62-4a5b-9788-9d4cd6e34999");
        String s1 = buKongService.delBuKongByIds(ss);
        System.out.println(s+"----"+s1);
        String s2 = buKongService.delBuKongByContorlType("99");
        System.out.println(s2);
    }
}
