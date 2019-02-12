package com.ferret.dao.photoReceive;


import com.ferret.bean.photoReceive.PhotoReceiveResult;
import com.ferret.bean.photoReceive.TokenTask;
import com.ferret.dao.photoReceive.provider.PhotoReceiveProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoReceiveMapper {

    @Select("select send_url from tb_token_url where token = #{token}")
    String findUrlByToken(String token);

    @Insert("insert into tb_token_task (token,task_id,camera_ip,image_path) values(#{token},#{taskId},#{cameraIp},#{imagePath})")
    int insertTokenTask(TokenTask tokenTask);

    @DeleteProvider(type = PhotoReceiveProvider.class,method = "delByIds")
    int deleteByids(@Param("ids")List<Integer> ids);

    @Select("SELECT c.send_url, a.id, a.task_id, b.clusterid FROM tb_token_task a, jh_cluster_pic b, tb_token_url c WHERE a.flag = 1 and  a.image_path = b.picpath AND a.token = c.token")
    @Results({
            @Result(column = "clusterid",property = "nPersonId")
    })
    List<PhotoReceiveResult> findFlageIsOne();
}
