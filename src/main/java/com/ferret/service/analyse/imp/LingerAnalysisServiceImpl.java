package com.ferret.service.analyse.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.ClusterPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferret.bean.TravelAtNeight;
import com.ferret.dao.LingerAnalysisMapper;
import com.ferret.service.analyse.LingerAnalysisService;
@Service
public class LingerAnalysisServiceImpl implements LingerAnalysisService {

	@Autowired
	private LingerAnalysisMapper lingerAnalysisMapper;
	/**
	 * 根据时间段查看出现次数在count以上的出行人的personid
	 * 根据personid查看代表图片
	 */

	@Override
	public Map<String, Object> findClusterByPersonId(int pageNum,
                                                     int pageSize,
                                                     String startTime,
                                                     String endTime,
                                                     int totalNum,
                                                     int minNum,
                                                     List<CameraInfo> cameraInfos) {
		Map<String, Object> resultMap = new HashMap<>();
		int startNum = (pageNum - 1) * pageSize;
		if (totalNum < 1 && pageNum==1) {
			// 如果页面返回了总条数并且是第一页,说明是第一次查询,查询总条数,否则不查询总条数
			int totalNumber = lingerAnalysisMapper.seachTotalByAnalysis(startTime,endTime,minNum,cameraInfos);
			resultMap.put("totalNum", totalNumber);
		}
		// 查询结果集
		List<ClusterPass> resultList = lingerAnalysisMapper.seachPersonIdByCount(startNum,pageSize,startTime,endTime,minNum,cameraInfos);
		for (int x = 0; x < resultList.size(); x++) {
			ClusterPass c = resultList.get(x);
			if (c.getCreateTime() != null) {
				c.setCreateTime(c.getCreateTime().substring(0, 19));
			}
		}
		resultMap.put("resultList", resultList);
		return resultMap;
	}

	/**
	 * @Descriptions 根据时间段查看出现次数在count以上的出行人的personid 根据personid查看代表图片
	 * @auThor hyl
	 */
	@Override
	public Map<String, Object> findClusterByPersonId(TravelAtNeight travelAtNeight) {
		Map<String, Object> resultMap = new HashMap<>();
		int startNum = (travelAtNeight.getPageNum() - 1) * travelAtNeight.getPageSize();
		travelAtNeight.setStartNum(startNum);
		if (travelAtNeight.getTotalNum() < 1 && travelAtNeight.getPageNum()==1) {
			// 如果页面返回了总条数并且是第一页,说明是第一次查询,查询总条数,否则不查询总条数
			int totalNum = lingerAnalysisMapper.seachTotalByAnalysis(travelAtNeight.getStartTime(),travelAtNeight.getEndTime(),
                    travelAtNeight.getMinNum(),travelAtNeight.getCameraList());
			resultMap.put("totalNum", totalNum);
		}
		// 查询结果集
		List<ClusterPass> resultList = lingerAnalysisMapper.seachPersonIdByCount(startNum,travelAtNeight.getPageSize(),travelAtNeight.getStartTime(),travelAtNeight.getEndTime(),
                travelAtNeight.getMinNum(),travelAtNeight.getCameraList());
		for (int x = 0; x < resultList.size(); x++) {
			ClusterPass c = resultList.get(x);
			if (c.getCreateTime() != null) {
				c.setCreateTime(c.getCreateTime().substring(0, 19));
			}
		}
		resultMap.put("resultList", resultList);
		return resultMap;
	}
	/**
	 * 根据personid查看条件内的频繁出行人的所有信息
	 * 根据personid查看详细信息
	 */
	@Override
	public Map<String, Object> findAnalysisResultByPersonId(TravelAtNeight travelAtNeight) {
		// 开始条数
		int startNum = (travelAtNeight.getPageNum() - 1) * travelAtNeight.getPageSize();
		travelAtNeight.setStartNum(startNum);
		// 查询结果集
		List<ClusterPass> resultList = lingerAnalysisMapper.seachAnalysisResultByPersonId(travelAtNeight.getStartTime(),
				travelAtNeight.getEndTime(),
				startNum,
				travelAtNeight.getPageSize(),
				travelAtNeight.getPersonId(),
				travelAtNeight.getCameraList());
		// 处理时间多了.0问题
		for (int x = 0; x < resultList.size(); x++) {
			ClusterPass c = resultList.get(x);
			if (c.getCreateTime() != null) {
				c.setCreateTime(c.getCreateTime().substring(0, 19));
			}
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", resultList);
		return resultMap;
	}

	@Override
	public Map<String, Object> findAnalysisResultByPersonId(String startTime, String endTime, int pageNum, int pageSize, String personId, List<CameraInfo> cameraInfos) {
		// 开始条数
		int startNum = (pageNum - 1) * pageSize;
		// 查询结果集
		List<ClusterPass> resultList = lingerAnalysisMapper.seachAnalysisResultByPersonId(startTime,endTime,startNum,pageSize,personId,cameraInfos);
		// 处理时间多了.0问题
		for (int x = 0; x < resultList.size(); x++) {
			ClusterPass c = resultList.get(x);
			if (c.getCreateTime() != null) {
				c.setCreateTime(c.getCreateTime().substring(0, 19));
			}
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", resultList);
		return resultMap;
	}
}
