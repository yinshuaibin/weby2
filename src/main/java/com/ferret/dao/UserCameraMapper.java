package com.ferret.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

import com.ferret.dao.provider.RoleCameraMapperProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCameraMapper {

	@InsertProvider(type = RoleCameraMapperProvider.class, method = "insertList")
	int insertList(String userId, List<String> cameraId);

	@Delete("DELETE FROM tb_user_camera WHERE user_id = #{userId}")
	int deleteByUserId(String userId);

	@Select("SELECT ci.* FROM tb_camera_info ci,tb_user_camera rc WHERE user_id =#{userId}")
	List<String> getCameraNumberByRoleId(@Param("userId") String userId);

	@Select("SELECT camera_number FROM tb_user_camera WHERE user_id = #{userId}")
	List<String> getCameraNumByUserId(String userId);

	@Insert("INSERT INTO tb_user_camera (user_id, camera_number) VALUES(#{userId},#{areaId})")
	int insertUserCamera(@Param("userId") String userId, @Param("areaId") String area_Id);

}
