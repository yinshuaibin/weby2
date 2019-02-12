package com.ferret.dao;

import com.ferret.bean.HistoryPass;
import com.ferret.dao.provider.HistoryPassMapperProvider;
import com.ferret.dto.HistoryPassDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistoryPassMapper {
	/**
	 * 查询: 查询历史通行表符合条件的记录
	 * @param cameraId
	 * @param startDateTime
	 * @param endDateTime
	 * @param pageNum
	 * @return
	 */
	@SelectProvider(type = HistoryPassMapperProvider.class, method = "findHistoryPassByTimeAndCameraId")
    List<HistoryPass> getHistoryPass(@Param("cameraId") List cameraId,
                                     @Param("startDateTime") String startDateTime,
                                     @Param("endDateTime") String endDateTime,
                                     @Param("pageNum") int pageNum,
									 @Param("pageSize") int pageSize);
	
	/**
	 * 查询: 查询历史通行表符合条件的记录总数
	 * @param cameraId
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	@SelectProvider(type = HistoryPassMapperProvider.class, method = "findHistoryPassCountByTimeAndCameraId")
    int getHistoryPassCount(@Param("cameraId") List cameraId,
                            @Param("startDateTime") String startDateTime,
                            @Param("endDateTime") String endDateTime);


    List<HistoryPassDTO> listHistoryPassDTO(Integer[] featureIds);

}