package com.ferret.dao;

import com.ferret.bean.AllopatryPersonAnalyse;
import com.ferret.bean.ClusterSearch;
import com.ferret.dao.provider.AllopatryAnalyseMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface AllopatryAnalyseMapper {

		@Deprecated
		@Select("CALL allopatryAnalyse_total(#{beginTimeA},#{endTimeA},#{cameraIpListA},#{beginTimeB},#{endTimeB},#{cameraIpListB})")
		Integer getAllopatryAnalyseTotal(@Param("beginTimeA")String beginTimeA, @Param("endTimeA")String endTimeA, @Param("beginTimeB")String beginTimeB, 
				@Param("endTimeB")String endTimeB,@Param("cameraIpListA")String cameraListA, @Param("cameraIpListB")String cameraListB);

		@Deprecated
		@Select("CALL allopatryAnalyse_data(#{beginTimeA},#{endTimeA},#{cameraIpListA},#{beginTimeB},#{endTimeB},#{cameraIpListB},#{startPageNum},#{pageSize})")
		@Results({
			@Result(property="personId",column="person_id"),
			@Result(property="cameraIp",column="camera_ip"),
			@Result(property="imagePath",column="image_path"),
			@Result(property="startTime",column="start_time"),
			@Result(property="endTime",column="end_time"),
			@Result(property="cameraIpB",column="camera_ipB"),
			@Result(property="imagePathB",column="image_pathB"),
			@Result(property="startTimeB",column="start_timeB"),
			@Result(property="endTimeB",column="end_timeB"),
			@Result(property="name",column="name"),
			@Result(property="idcard",column="idcard"),
			@Result(property="cameraNameA",column="cameraNameA"),
			@Result(property="cameraNameB",column="cameraNameB")
		})
		List<AllopatryPersonAnalyse> getAllopatryAnalyse(@Param("beginTimeA")String beginTimeA,@Param("endTimeA")String endTimeA,
				@Param("cameraIpListA")String cameraListA, @Param("beginTimeB")String beginTimeB, @Param("endTimeB")String endTimeB,
				@Param("cameraIpListB")String cameraListB, @Param("startPageNum")int startPageNum, @Param("pageSize")int pageSize);

		List<Map<String,Integer>> findAllopatryAnalyse(@Param("beginTimeA")String beginTimeA, @Param("endTimeA")String endTimeA,
												  @Param("cameraIpsA")List<String> cameraIpsA, @Param("beginTimeB")String beginTimeB, @Param("endTimeB")String endTimeB,
												  @Param("cameraIpsB")List<String> cameraIpsB);

		/**
		 * 区域碰撞分析（查询该personId符合A条件，或者符合B条件的数据）
		 */
		@SelectProvider(type = AllopatryAnalyseMapperProvider.class,method = "getAllopatryPersonInfo")
		@Results({
				@Result(property = "personId",column = "person_id"),
				@Result(property = "cameraIp",column = "camera_ip"),
				@Result(property = "imagePath",column = "image_path"),
				@Result(property = "createTime",column = "start_time"),
				@Result(property = "cameraName",column = "name")
		})
		List<ClusterSearch> getAllopatryPersonInfo(@Param("personId")String personId, @Param("beginTimeA")String beginTimeA, @Param("endTimeA")String endTimeA,
												   @Param("cameraNumbersListA")ArrayList<String> cameraNumbersListA, @Param("beginTimeB")String beginTimeB,
												   @Param("endTimeB")String endTimeB, @Param("cameraNumbersListB")ArrayList<String> cameraNumbersListB,
												   @Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);

	/**
	 * 区域碰撞分析 总条数（查询该personId符合A条件，或者符合B条件的数据 ）
	 */
	@SelectProvider(type = AllopatryAnalyseMapperProvider.class,method = "getAllopatryPersonTotal")
	Integer getAllopatryPersonTotal(@Param("personId")String personId, @Param("beginTimeA")String beginTimeA, @Param("endTimeA")String endTimeA,
											   @Param("cameraNumbersListA")ArrayList<String> cameraNumbersListA, @Param("beginTimeB")String beginTimeB,
											   @Param("endTimeB")String endTimeB, @Param("cameraNumbersListB")ArrayList<String> cameraNumbersListB);

}
