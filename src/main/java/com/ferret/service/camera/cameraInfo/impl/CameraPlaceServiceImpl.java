package com.ferret.service.camera.cameraInfo.impl;

import com.ferret.bean.CameraPlace;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.CameraPlaceMapper;
import com.ferret.service.camera.cameraInfo.CameraPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 相机场所管理service
 * y 0125
 */
@Service
public class CameraPlaceServiceImpl implements CameraPlaceService {

    @Autowired
    private CameraPlaceMapper cameraPlaceMapper;

    @Autowired
    private CameraInfoMapper cameraInfoMapper;


    /**
     * 添加新的相机场所
     * @param cameraPlace 相机场所实体类
     * @return
     */
    @Override
    public int insertCameraPlace(CameraPlace cameraPlace) {
       return cameraPlaceMapper.insertCameraPlace(cameraPlace);
    }

    /**
     * 删除某个相机场所
     * @param id
     * @return
     */
    @Override
    public int delCameraPalceById(int id) {
        // 查询是否使用了场所
        int countByCameraPlaceId = cameraInfoMapper.findCountByCameraPlaceId(id);
        if(countByCameraPlaceId > 0){
            // 如果使用,则返回0,不让其删除
            return 0;
        }
        // 返回删除条数
        return cameraPlaceMapper.delCameraPalceById(id);
    }

    /**
     * 修改相机场所信息
     * @param cameraPlace
     * @return
     */
    @Override
    public int updateCameraPlaceById(CameraPlace cameraPlace) {
        return cameraPlaceMapper.updateCameraPlaceById(cameraPlace);
    }

    /**
     * 根据相机场所id修改相机场所是否启用
     * @param id
     * @param status 1 启用  0 暂停使用
     * @return
     */
    @Override
    public int updateStatusById(int id,int status) {
        return cameraPlaceMapper.updateStatusById(id,status);
    }

    /**
     * 分页查询所有相机场所
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<CameraPlace> findAllCameraPlace(int pageNum, int pageSize) {
        int startNum = (pageNum - 1) * pageSize;
        return cameraPlaceMapper.findAllCameraPlace(startNum,pageSize);
    }

    /**
     * 查询相机场所总条数
     * @return
     */
    @Override
    public int findAllCameraPlaceCount() {
        return cameraPlaceMapper.findAllCameraPlaceCount();
    }

    /**
     * 校验是否有重复的number
     * @param number
     * @return
     */
    @Override
    public int findCountByNumber(String number) {
        return cameraPlaceMapper.findCountByNumber(number);
    }
}
