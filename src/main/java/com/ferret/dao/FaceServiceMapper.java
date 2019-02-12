package com.ferret.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ferret.bean.searchimage.FaceService;

public interface FaceServiceMapper {
	/**
	 * 获取faceServiceConfig的全部信息
	 * @param 
	 * @return List<FaceServiceConfig>
	 */
	@Select("select id,name,value,memo from tb_face_service_config ")
	@Results({
		@Result(property="id",column="id"),
		@Result(property="name",column="name"),
		@Result(property="value",column="value"),
		@Result(property="describe",column="memo")
	})
	List<FaceService> queryFaceService();
}
