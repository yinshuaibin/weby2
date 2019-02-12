package com.ferret.controller;

import com.ferret.bean.Base64ImageEntity;
import com.ferret.bean.CameraInfo;
import com.ferret.bean.DynamicQueryEntity;
import com.ferret.bean.ImageUrl;
import com.ferret.bean.InterfaceBean.Dynamic;
import com.ferret.service.dynamic.ImageDynamicService;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.webservice.client.QueryResult;
import com.ferret.webservice.impl.ImageFeatureWebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态对比接口,对图片提取特征,进行查询进度
 * @author cc;
 * @since 2018/1/13;
 */
@RestController
public class ImageFeatureController extends BaseController{

    @Autowired
    private ImageFeatureWebServiceImpl imageFeatureWebService;

    @Autowired
    private ImageDynamicService imageDynamicService;

    /**
     * 动态特征查询,返回查询任务task
     * @param entity
     * @return
     */
    @RequestMapping(value = "/features/task", method = RequestMethod.POST)
    public ResponseEntity getFeatureTask(@RequestBody DynamicQueryEntity entity){
       return imageFeatureWebService.queryDynamicImagesByFeature(entity);
    }

    /**
     * 根据查询任务task,获取查询进度,完成则返回结果
     * @param task
     * @return
     */
    @RequestMapping(value = "/features/task/{id}",method = RequestMethod.GET)
    public ResponseEntity getQueryProcessingByTaskId(@PathVariable("id") String task){
       QueryResult queryResult = imageFeatureWebService.getQueryProcessingByTask(task);
       return ResponseEntity.ok(queryResult);
    }
    /**
     * @Description 动态检索
     * @param entity
     * @date 2019-01-02 17:50:40
     * @author xieyingchao
     */
    @RequestMapping(value = "/dynamic", method = RequestMethod.POST)
    public ResponseEntity getDynamic(@RequestBody DynamicQueryEntity entity){
        String imageData = entity.getImageData();
        if(imageData.startsWith("http") && imageData.length() < 500) {
            imageData = ImageBase64Utils.generateBase64ImageEntity(imageData).getImageData();
        }
        List<CameraInfo> cameraInfos = entity.getCameraList();
        List<String> cameras = new ArrayList<>();
        for (CameraInfo cameraInfo:cameraInfos) {
            cameras.add(cameraInfo.getCameraId());
        }
        List<Dynamic> dynamics =  imageDynamicService.DynamicFaceTrauls(
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getThreshold(),
                imageData,
                cameras,
                "jh",
                entity.getCount());
        return ResponseEntity.ok(dynamics);
    }
}
