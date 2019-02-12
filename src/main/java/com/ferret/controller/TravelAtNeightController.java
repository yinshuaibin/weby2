package com.ferret.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ferret.bean.TravelAtNeight;
import com.ferret.service.analyse.TravelAtNeightService;

/**
 * 夜行人Controller
 * @author y
 * 日期:0720
 * 夜间出行方法需要参数过多,使用实体类更加方便,所以不再修改
 */
@RestController
public class TravelAtNeightController extends BaseController {
	
	@Autowired
	private TravelAtNeightService travelAtNeightService;

	/**
	 * 根据页面传递的用户所选地点,四个时间,最小出行次数,查询出频繁夜行人
	 * @param travelAtNeight 夜行人实体类
	 * @return totalNum:总数
	 * resultList:数据集
	 */
	@RequestMapping(value="/findTravelAtNeight",method=RequestMethod.POST)
	public Map<String,Object> findTravelAtNeight(@RequestBody TravelAtNeight travelAtNeight){
		return travelAtNeightService.findTravelAtNeight(travelAtNeight);
	}
	
	/**
	 * 查询出夜行人以后,根据夜行人的person_id查询对应的出行记录
	 * @param travelAtNeight
	 * @return
	 */
	@RequestMapping(value="/findTravelAtNeightByPersonId",method=RequestMethod.POST)
	public Map<String,Object> findTravelAtNeightByPersonId(@RequestBody TravelAtNeight travelAtNeight){
		return travelAtNeightService.findTravelAtNeightByPersonId(travelAtNeight);
	}
}
