package com.ferret.controller;

import com.ferret.bean.CameraPlace;
import com.ferret.service.camera.cameraInfo.CameraPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相机场所管理
 * y  0124
 */
@RestController
public class CameraPlaceController extends BaseController {


    @Autowired
    private CameraPlaceService cameraPlaceService;


    /**
     * 添加新的相机场所
     * @param cameraPlace 相机场所实体类
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/insertCameraPlace")
    public int insertCameraPlace(@RequestBody CameraPlace cameraPlace) {
        return cameraPlaceService.insertCameraPlace(cameraPlace);
    }

    /**
     * 删除某个相机场所
     * @param id
     * @return
     */
    @RequestMapping("/delCameraPlace")
    public int delCameraPalceById(int id) {
        return cameraPlaceService.delCameraPalceById(id);
    }

    /**
     * 修改相机场所信息
     * @param cameraPlace
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/updateCameraPlace")
    public int updateCameraPlaceById(@RequestBody CameraPlace cameraPlace) {
        return cameraPlaceService.updateCameraPlaceById(cameraPlace);
    }

    /**
     * 根据相机场所id修改相机场所是否启用
     * @param id
     * @param status 1 启用  0 暂停使用
     * @return
     */
    @RequestMapping("/updateStatus")
    public int updateStatusById(int id,int status) {
        return cameraPlaceService.updateStatusById(id,status);
    }

    /**
     * 分页查询所有相机场所
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/findAllCameraPlace")
    public Map findAllCameraPlace(int pageNum, int pageSize) {
        Map resultMap = new HashMap();
        // 只有在第一页的时候才查询总条数
        if(pageNum == 1){
            int allCameraPlaceCount = cameraPlaceService.findAllCameraPlaceCount();
            resultMap.put("totalNum",allCameraPlaceCount);
        }
        List<CameraPlace> allCameraPlace = cameraPlaceService.findAllCameraPlace(pageNum, pageSize);
        resultMap.put("resultList",allCameraPlace);
        return resultMap;
    }

    /**
     * 校验相机场所number
     * @param number
     * @return
     */
    @RequestMapping("/checkNumber")
    public int findCountByNumber(String number){
        return cameraPlaceService.findCountByNumber(number);
    }
}
