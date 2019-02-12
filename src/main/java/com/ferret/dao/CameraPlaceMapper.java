package com.ferret.dao;

import com.ferret.bean.CameraPlace;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraPlaceMapper {

    @Insert("insert into tb_dictionary (number,name,enabled) values(#{number},#{name},1)")
    int insertCameraPlace(CameraPlace cameraPlace);

    @Delete("Delete from tb_dictionary where id = #{id}")
    int delCameraPalceById(int id);

    @Update("update tb_dictionary set number =#{number},name=#{name},enabled=#{enabled} where id = #{id}")
    int updateCameraPlaceById(CameraPlace cameraPlace);

    /**
     * 根据相机场所id修改相机场所是否启用
     * @param id
     * @param status 1 启用  0 暂停使用
     * @return
     */
    @Update("update tb_dictionary set enabled=#{status} where id = #{id}")
    int updateStatusById(int id,int status);

    @Select("select * from tb_dictionary  limit #{startNum},#{pageSize}")
    List<CameraPlace>  findAllCameraPlace(@Param("startNum") int startNum, @Param("pageSize") int pageSize);

    @Select("select count(id) from tb_dictionary")
    int findAllCameraPlaceCount();

    /**
     * 根据number查询是否有重复的number
     * @param number
     * @return
     */
    @Select("select count(id) from tb_dictionary where number = #{number}")
    int findCountByNumber(String number);
}
