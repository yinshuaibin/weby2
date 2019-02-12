package com.ferret.service.analyse;

import java.util.Map;

import com.ferret.bean.TravelAtNeight;

/**
 * 夜行人service
 * @author y
 * 时间:0720
 *
 */
public interface TravelAtNeightService {

	/**
	 * 根绝所选地区,四个时间,来查询夜行频繁出行人
	 * @param travelAtNeight 夜行人实体类
	 * @return
	 */
	public Map<String,Object> findTravelAtNeight(TravelAtNeight travelAtNeight);

	/**
	 * 查询出夜行人以后,根据夜行人的person_id查询对应的出行记录
	 * @param travelAtNeight
	 * @return
	 */
	public Map<String, Object> findTravelAtNeightByPersonId(TravelAtNeight travelAtNeight);
}
