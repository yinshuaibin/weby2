package com.ferret.service.analyse;

import java.util.List;
import java.util.Map;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.TravelAtNeight;

public interface LingerAnalysisService {
    @Deprecated
	Map<String, Object> findClusterByPersonId(TravelAtNeight strang);

	Map<String, Object> findClusterByPersonId(int pageNum,
											  int pageSize,
											  String startTime,
											  String endTime,
											  int totalNum,
											  int minNum,
                                              List<CameraInfo> cameraInfos);
	/**
	 * 查询出频繁出行人后，根据person_id查询对应的出行记录
	 * @param travelAtNeight
	 * @return
	 */
	@Deprecated
	public Map<String, Object> findAnalysisResultByPersonId(TravelAtNeight travelAtNeight);
	/**
	 * 查询出频繁出行人后，根据person_id查询对应的出行记录
	 * @return
	 */
	public Map<String, Object> findAnalysisResultByPersonId (String startTime, String endTime, int pageNum, int pageSize, String personId, List<CameraInfo> cameraInfos);

}
