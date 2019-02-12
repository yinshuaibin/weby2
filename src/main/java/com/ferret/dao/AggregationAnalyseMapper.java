package com.ferret.dao;

import com.ferret.bean.AggregationAnalyse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregationAnalyseMapper {

	/**
	 * 通过相机id和时间段 分析报警表数据   卡口，人数，次数
	 */
	@Select("CALL aggregationAnalyseCount(#{bukongGroupId},#{beginTime},#{endTime})")
	@Results({
			@Result(property="cameraIp",column="camera_ip"),
			@Result(property="personNum",column="personNum"),
			@Result(property="countNum",column="countNum"),
			@Result(property="cameraName",column="cameraname")
	})
	List<AggregationAnalyse> aggregationAnalyseResult_new(@Param("bukongGroupId")String bukongGroupId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
}
