package com.ferret.controller.photoReceive;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ferret.controller.BaseController;
import com.ferret.service.photoReceive.PhotoReceiveService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class PhotoReceiveController extends BaseController {

    @Autowired
    private PhotoReceiveService photoReceiveService;

    @RequestMapping(value="/PhotoReceive", method= RequestMethod.POST)
    public String makeTask(@RequestBody Map reqMap){
        Map resultMap = new HashMap();
        resultMap.put("resultcode",1);
        // 验证token
        String token = (String)reqMap.get("token");
        if(StringUtils.isBlank(token)){
            resultMap.put("message","token为空");
            return JSON.toJSONString(reqMap);
        }
        String urlByToken = photoReceiveService.findUrlByToken(token);
        if(StringUtils.isBlank(urlByToken)){
            resultMap.put("message","token错误");
            return JSON.toJSONString(reqMap);
        }
        // 验证base64是否是图片
        String base64 = (String) reqMap.get("file1");
        if(StringUtils.isBlank(base64)){
            resultMap.put("message","传递图片为空");
            return JSON.toJSONString(reqMap);
        }
        if(!isImageFromBase64(base64)){
            resultMap.put("message","传递的base64字符不符合要求");
            return JSON.toJSONString(reqMap);
        }
        String sendImagePath = (String) reqMap.get("filename");
        if(StringUtils.isBlank(sendImagePath)){
            resultMap.put("message","传递的图片名称为空");
            return JSON.toJSONString(reqMap);
        }
        return photoReceiveService.makeTask(token,base64,sendImagePath);
    }

    /**
     * 校验图片base64是否符合要求
     * @param base64Str
     * @return
     */
    synchronized private static boolean isImageFromBase64(String base64Str) {
        boolean flag = false;
        try {
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64Str)));
            if (null == bufImg) {
                return flag;
            }
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return flag;
    }
}
