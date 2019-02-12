package com.ferret.open.dao;

import com.ferret.open.bean.ClusterDataImage;
import com.ferret.open.bean.ClusterDataWei;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterDataPushDao {

    // @Select("SELECT id,person_id,camera_ip,image_path,image_time FROM tb_cluster_image WHERE id > #{id} ORDER BY id")
    @Select("SELECT id,clusterid person_id,cameraip camera_ip,picpath image_path,pictime image_time FROM jh_cluster_pic WHERE id > #{id} ORDER BY id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "personId", column = "person_id"),
            @Result(property = "cameraIp", column = "camera_ip"),
            @Result(property = "imagePath", column = "image_path"),
            @Result(property = "imageTime", column = "image_time")
    })
    List<ClusterDataImage> clusterDataPush(@Param("id") String id);


    @Select("SELECT status FROM tb_clusterimg_timer WHERE clusterimg_name = 'clusterDataPush'")
    /**
     * 获取推送的位置（cluster_image表中主键id）
     */
    String firstClusterDataPush();

    @Update("UPDATE tb_clusterimg_timer SET status = #{id} where clusterimg_name = 'clusterDataPush'")
    void updateClusterImgId(@Param("id")String id);

    /**
     * 维族人 获取推送的位置（cluster_wei表中主键create_time）
     */
    @Select("SELECT clusterimg_time FROM tb_clusterimg_timer WHERE clusterimg_name = 'clusterWeiPush'")
    String firstClusterWeiPush();

    @Select("SELECT person_id,create_time,idcard,name,represent_img1,represent_img5,count FROM tb_cluster_wei WHERE create_time > #{createTime} ORDER BY create_time")
    @Results({
            @Result(property = "personId", column = "person_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "idcard", column = "idcard"),
            @Result(property = "name", column = "name"),
            @Result(property = "representImg1", column = "represent_img1"),
            @Result(property = "representImg5", column = "represent_img5"),
            @Result(property = "count", column = "count")
    })
    List<ClusterDataWei> clusterWeiData(@Param("createTime")String createTime);

    @Update("UPDATE tb_clusterimg_timer SET clusterimg_time = #{timeSing} where clusterimg_name = 'clusterWeiPush'")
    void updateClusterWeiTime(@Param("timeSing") String timeSing);
}
