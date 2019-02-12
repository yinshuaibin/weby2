package com.ferret.service.camera.cameraInfo;

import com.ferret.bean.CameraPlace;

import java.util.List;

public interface CameraPlaceService {

    /**
     * 添加新的相机场所
     * @param cameraPlace 相机场所实体类
     * @return
     */
    int insertCameraPlace(CameraPlace cameraPlace);

    /**
     * 删除某个相机场所
     * @param id
     * @return
     */
    int delCameraPalceById(int id);

    /**
     * 修改相机场所信息
     * @param cameraPlace
     * @return
     */
    int updateCameraPlaceById(CameraPlace cameraPlace);

    /**
     * 根据相机场所id修改相机场所是否启用
     * @param id
     * @param status 1 启用  0 暂停使用
     * @return
     */
    int updateStatusById(int id,int status);

    /**
     * 分页查询所有相机场所
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<CameraPlace> findAllCameraPlace(int pageNum, int pageSize);

    /**
     * 查询相机场所总条数
     * @return
     */
    int findAllCameraPlaceCount();

    /**
     * 校验是否有重复的编号number
     * @param number
     * @return
     */
    int findCountByNumber(String number);
}
