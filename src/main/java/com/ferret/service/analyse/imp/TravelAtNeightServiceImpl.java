package com.ferret.service.analyse.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ferret.bean.ClusterPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferret.bean.TravelAtNeight;
import com.ferret.dao.TravelAtNeightMapper;
import com.ferret.service.analyse.TravelAtNeightService;

/**
 * 夜行人serviceImpl
 * 
 * @author y 日期:0720
 *
 */
@Service
public class TravelAtNeightServiceImpl implements TravelAtNeightService {

	@Autowired
	private TravelAtNeightMapper travelAtNeightMapper;

	/**
	 * 根据所选地区,四个时间,来查询夜行频繁出行人
	 * 
	 * @param travelAtNeight
	 *            夜行人实体类
	 * @return
	 */
	@Override
	public Map<String, Object> findTravelAtNeight(TravelAtNeight travelAtNeight) {
		Map<String, Object> resultMap = new HashMap<>();
		// 开始条数
		int startNum = (travelAtNeight.getPageNum() - 1) * travelAtNeight.getPageSize();
		travelAtNeight.setStartNum(startNum);
		if (travelAtNeight.getTotalNum() < 1 && travelAtNeight.getPageNum() == 1) {
			// 如果页面返回了总条数并且是第一页,说明是第一次查询,查询总条数,否则不查询总条数
			int totalNum = travelAtNeightMapper.findTravelAtNeightTotal(travelAtNeight);
			resultMap.put("totalNum", totalNum);
		}
		// 查询结果集
		List<ClusterPass> resultList = travelAtNeightMapper.findTravelAtNeightList(travelAtNeight);
		// 处理时间多了.0问题
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
	 * 查询出夜行人以后,根据夜行人的person_id查询对应的出行记录
	 * 
	 * @param travelAtNeight
	 * @return
	 */
	@Override
	public Map<String, Object> findTravelAtNeightByPersonId(TravelAtNeight travelAtNeight) {
		// 开始条数
		int startNum = (travelAtNeight.getPageNum() - 1) * travelAtNeight.getPageSize();
		travelAtNeight.setStartNum(startNum);
		// 查询结果集
		List<ClusterPass> resultList = travelAtNeightMapper.findTravelAtNeightByPersonId(travelAtNeight);
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
