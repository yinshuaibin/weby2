package com.ferret.service.camera.cameragroup;

import com.ferret.bean.CameraGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.service.cameragroup;
 * @auth: Administrator;
 * @since: 2017/12/21 0021;
 * @desc:
 */
public interface CameraGroupService {
    /**
     * 查询城市下的分组
     * @param number
     * @return
     */
    List<Map<String,Object>> findByCameraGroupNumber(@Param("number") String number);
    /**
     * @Description 分页查询城市下的分组
     * @param number
     * @param pageNum
     * @param pageSize 
     * @date 2019-01-02 17:20:03
     * @author xieyingchao
     */
    List<CameraGroup> findByCameraGroupCount(String number,Integer pageNum, Integer pageSize);

    /**
     * @Description 
     * @param number 查询城市下的分组总数
     * @date 2019-01-02 17:19:38
     * @author xieyingchao
     */
    int findByCameraGroupTotalNum(String number);
    /**
     * 查询所有相机数据
     * @return
     */
    List<Map<String,Object>>  findAllCameraGroup();

    /**
     * 增加相机信息数据
     * @param cameraGroup
     */
    CameraGroup saveCameraGroup(CameraGroup cameraGroup);

    /**
     * 修改相机信息数据
     * @param cameraGroup
     */
    void updateCameraGroup(CameraGroup cameraGroup);

    /**
     * 根据Id查询相机信息数据
     * @param id
     * */
    CameraGroup findById(@Param("id")Integer id);

    /**
     * 根据ID删除相机信息数据
     * @param id
     */
    void deleteCameraGroup(@Param("id")Integer id);

    
	int selectSquences();

    /**
     * 根据name查询相机信息数据
     * @param name
     */
    int selectCarmeraGroupByName(String name);
}
