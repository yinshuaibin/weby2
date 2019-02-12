package com.ferret.dao;

import com.ferret.bean.InterfaceBean.Dynamic;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigInteger;

/**
 * @ClassName RealTimeImageMapper
 * @Description 实时抓拍表对应sql
 * @Author xieyingchao
 * @Date 2019-01-02 14:11
 * @Version 1.0
 **/
public interface RealTimeImageMapper {

    /** 根据featureId查询抓拍信息 */
    @Select("SELECT picpath,snaptime,createtime,cameraid FROM jh_facepic WHERE featureid = #{featureId}")
    @Results({
            @Result(property = "PicFileName", column = "picpath"),
            @Result(property = "snapTime", column = "snaptime"),
            @Result(property = "createTime", column = "createtime"),
            @Result(property = "cameraInfo", column = "cameraid", one = @One(select = "com.ferret.dao.CameraInfoMapper.getCameraInfo", fetchType = FetchType.EAGER))})
    Dynamic getRealTimeImageAndCamera(@Param("featureId") BigInteger featureId);
}
