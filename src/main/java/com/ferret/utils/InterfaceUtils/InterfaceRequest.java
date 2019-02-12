package com.ferret.utils.InterfaceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ferret.bean.InterfaceBean.FaceImageResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class InterfaceRequest {
    private static RestTemplate restTemplate = new RestTemplate();
    public static String interfaceRequest(String url,Object jsonParam){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json");
        headers.setContentType(type);
        HttpEntity<Object> formEntity = new HttpEntity<Object>(jsonParam, headers);
        String rs = restTemplate.postForObject(url, formEntity, String.class);
        return  rs;
    }

    public static String interfaceResultIsFaceImageResult(String url, Object jsonParam){
        String rs = InterfaceRequest.interfaceRequest(url,jsonParam);
        //转换成对应的结果实体类
        FaceImageResult faceImageResult = JSON.parseObject(rs, new TypeReference<FaceImageResult>() {
        });
        if(faceImageResult.getResultcode()!=null && faceImageResult.getStatus()!=null){
            //调用工具类,返回接口的查询结果
            return ResultCodeUtils.errMessage(faceImageResult.getResultcode(), faceImageResult.getStatus());
        }
        return "接口服务出现异常,请检查!";
    }
}
