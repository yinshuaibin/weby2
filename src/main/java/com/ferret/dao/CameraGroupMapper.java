package com.ferret.dao;

import com.ferret.bean.CameraGroup;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface CameraGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CameraGroup record);

    int insertSelective(CameraGroup record);

    CameraGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CameraGroup record);

    int updateByPrimaryKey(CameraGroup record);

    /**
     * 查询所有信息
     * @return
     */
    @Select("SELECT *FROM tb_camera_group")
    List<Map<String,Object>>  findAllCameraGroup();

    /**
     * 查询摄像所对应的城市区域
     * @param number   对应城市表的code
     * @return
     */
    @Select("select * from tb_camera_group where number REGEXP #{number}")
    List<Map<String,Object>> findByCameraGroupNumber(@Param("number") String number);

    /**
     * @Description  分页查询摄像所对应的城市区域相机分组，并查询分组下面相机数量
     * @param number 地图ID
     * @param startNum 开始个数
     * @param pageSize 每页个数
     * @date 2019-01-02 17:13:51
     * @author xieyingchao
     */
    @Select("SELECT a.id,a.name,a.description,a.place_id,a.number,IFNULL(b.count,0) count,b.groupid from " +
            " (select * from tb_camera_group where number REGEXP #{number}) a LEFT JOIN " +
            "(SELECT COUNT(groupid) count,groupid FROM jh_camera " +
            "WHERE groupid REGEXP #{number} AND status = 1 GROUP BY groupid) b on a.number = b.groupid LIMIT #{startNum},#{pageSize}")
    List<CameraGroup> findByCameraGroupCount(@Param("number") String number,@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize);

    /**
     * @Description 查询摄像所对应的城市区域相机分组总数
     * @param number 对应城市的code
     * @date 2019-01-02 17:16:46
     * @author xieyingchao
     */
    @Select("SELECT count(a.id) from " +
            " (select * from tb_camera_group where number REGEXP #{number}) a LEFT JOIN " +
            "(SELECT COUNT(groupid) count,groupid FROM jh_camera " +
            "WHERE groupid REGEXP #{number} AND status = 1 GROUP BY groupid) b on a.number = b.groupid")
    int findByCameraGroupTotalNum(@Param("number") String number);
    /**
     * 增加相机分组数据
     * @param cameraGroup
     * @return
     */
    @Insert("insert into " +
            "tb_camera_group (name,description,place_id,number) " +
            "values" +
            "(#{name, jdbcType=VARCHAR},#{description, jdbcType=VARCHAR},#{placeId, jdbcType=VARCHAR},#{number, jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCameraGroup(CameraGroup cameraGroup);

    /**
     * 修改相机分组信息数据
     * @param cameraGroup
     * @return
     */
    @Update("update tb_camera_group " +
            "set " +
            "name=#{name},description=#{description}," +
            "place_id=#{placeId},number=#{number} " +
            "where id=#{id}")
    int updateCameraGroup(CameraGroup cameraGroup);

    /**
     * 根据id删除相机分组数据
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_camera_group WHERE id=#{id}")
    int deleteCameraGroup(@Param("id")Integer id);

    /**
     * 根据iD进行查询
     * @param id
     * @return
     */
    @Select("SELECT * FROM tb_camera_group WHERE id=#{id}")
    CameraGroup findById(@Param("id")Integer id);

    /**
     * 根据name进行查询
     * @param name
     * @return
     */
    @Select("SELECT * FROM tb_camera_group WHERE name=#{name}")
    List<CameraGroup> findByName(@Param("name")String name);
}