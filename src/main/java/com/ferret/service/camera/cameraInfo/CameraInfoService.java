package com.ferret.service.camera.cameraInfo;

import com.ferret.bean.CameraInfo;
import com.ferret.dto.PageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.service.cameraInfoservice;
 * @auth: Administrator;
 * @since: 2017/08/03 0015;
 * @desc:
 * 修改人:hyl
 * 修改原因:新增相机名字ip不重名的方法
 */
public interface CameraInfoService {
    /**
     * 查询摄像所在的城市区域
     * @param group_id  对应相机id字段值
     * @return
     */
    List<CameraInfo>   findByCameraInfoByNumber(@Param("groupId") String group_id);

    /**
     * 增加摄像数据
     * @param cameraInfo
     */
    CameraInfo saveCameraInfo(CameraInfo cameraInfo);

    /**
     * 查看相机是否重名
     * @param name
     * @return
     */
    int checkCameraName(String name,Integer id);

    /**
     * 查看相机ip是否重复
     * @param ip
     * @return
     */
    int checkCameraIp(String ip,Integer id);

    /**
     * 查看相机ip是否被占用
     * @param ip,id
     * @return
     */
    int checkCameraIpOccupy(Integer id,String ip);
    /**
     * 查看相机ip是否被占用
     * @param ip,id
     * @return
     */
    int delectcheckIp(String ip);
    /**
     * 修改摄像数据
     * @param cameraInfo
     */
    int updateCameraInfo(CameraInfo cameraInfo);

    /**
     * 删除摄像数据
     * @param id
     */
    void deleteCameraInfo(@Param("id")Integer id);

    /**
     * 根据ID进行查询
     * @param id
     * @return
     */
    CameraInfo findByID(@Param("id")Integer id);

    /**
     * 获取总条数
     */
    int findCount();

    /**
     * 获取用户权限下对应的摄像头,并分页
     * @param pageNo 页码
     * @param pageSize 页大小
     * @param userId 角色id
     * @return pageDTO
     *
     * @author cc
     * @since 2018-03-09 15:52:27
     */
    PageDTO<CameraInfo> listCamerasByUserId(Integer pageNo, Integer pageSize, String userId);


    /**
     * 获取 已添加相机的数量
     * @return
     */
    Map addedCameraNum();
}
