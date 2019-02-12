package com.ferret.controller.photoReceive;

import com.alibaba.fastjson.JSON;
import com.ferret.bean.photoReceive.PhotoReceiveResult;
import com.ferret.dao.photoReceive.PhotoReceiveMapper;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PhotoReceiveControllerTest {

    @Autowired
    private PhotoReceiveController photoReceiveController;

    @Autowired
    private PhotoReceiveMapper photoReceiveMapper;

    @Test
    public void ters1(){
        Map reqMap = new HashMap();
        reqMap.put("token","23456");
        reqMap.put("file1", ImageBase64Utils.getImageStr("d:\\eclipse\\111.jpg"));
        reqMap.put("filename","192.168.1.175_01_205008211530010011_face"+"_test23456.jpg");
        String s = photoReceiveController.makeTask(reqMap);
        System.out.println(s);
    }


    @Test
    public void testMakeTask(){
        List<PhotoReceiveResult> flageIsOne = photoReceiveMapper.findFlageIsOne();
        if(flageIsOne !=null && flageIsOne.size()>1){
            // 按照不同的ulr发送出去,有多少个url就发送多少次
            Set<String> urlSet = new HashSet<>();
            for (PhotoReceiveResult p : flageIsOne){
                String sendUrl = p.getSendUrl();
                if (urlSet.size() < 1 || !urlSet.contains(sendUrl)){
                    List<PhotoReceiveResult> list = new ArrayList<>();
                    // id的集合,删除时使用
                    List<Integer> delIds = new ArrayList<>();
                    for(PhotoReceiveResult p1 : flageIsOne){
                        if(sendUrl.equals(p1.getSendUrl())){
                            delIds.add(p1.getId());
                            list.add(p1);
                        }
                    }
                    try {
                        System.out.println("--------"+JSON.toJSONString(list));
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.postForEntity(sendUrl, list,Object.class);
                        //photoReceiveMapper.deleteByids(delIds);
                    }catch (Exception e){
                        //e.printStackTrace();
                        log.error("推送地址{}出现错误,请校验此地址,该url的请求数据不再删除,错误信息为:{}",sendUrl,e.getMessage());
                    }
                }
                urlSet.add(sendUrl);
            }
        }
    }
}
