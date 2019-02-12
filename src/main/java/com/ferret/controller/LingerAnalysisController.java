package com.ferret.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ferret.bean.CameraInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ferret.bean.TravelAtNeight;
import com.ferret.service.analyse.LingerAnalysisService;

@RestController
public class LingerAnalysisController extends BaseController{
	/**
	 * 根据时间段查询数量在count以上的出行人
	 * @author huyunlong
	 * @param count  频次
	 * @return clusterdate实体对象
	 */
	@Autowired
	private LingerAnalysisService liAnalysisService;

	@Deprecated
	@RequestMapping(value = "/findLingerAnalysis" /*,method=RequestMethod.POST*/ )
	public Map<String, Object> findLingerByCount(@RequestBody TravelAtNeight strang){
		Map<String, Object> list = liAnalysisService.findClusterByPersonId(strang);
		return list;
	}
//    @RequestMapping(value = "/findLingerAnalysis" /*,method=RequestMethod.POST*/ )
    public Map<String, Object> findLingerByCount(@RequestBody Map map){
        List<String> stringList = (List<String>) map.get("cameraList");
        List<CameraInfo> cameraInfos = JSON.parseArray(JSONArray.toJSONString(stringList.toArray()),CameraInfo.class);
        Map<String, Object> list = liAnalysisService.findClusterByPersonId((Integer)(map.get("pageNum")),
                (Integer)(map.get("pageSize")),
                (String)(map.get("startTime")),
                (String)(map.get("endTime")),
                (Integer)(map.get("minNum")),
                (Integer)(map.get("totalNum")),
                        cameraInfos);
        return list;
    }

	/**
	 * 根据频繁出行人的的person_id查询对应的出行记录
	 * @param travelAtNeight
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="/findAnalysisByPersonId",method=RequestMethod.POST)
	public Map<String,Object> findTravelAtNeightByPersonId(@RequestBody TravelAtNeight travelAtNeight){
		return liAnalysisService.findAnalysisResultByPersonId(travelAtNeight);
	}
    /**
     * @Descriptions 根据频繁出行人的的person_id查询对应的出行记录
     * @param map
     * @Author 修改人：xyc 2018/12/17
     * @return
     */
//    @RequestMapping(value="/findAnalysisByPersonId",method=RequestMethod.POST)
    public Map<String,Object> findTravelAtNeightByPersonId(@RequestBody Map map){
        List<String> stringList = (List<String>) map.get("cameraList");
        List<CameraInfo> cameraInfos = JSON.parseArray(JSONArray.toJSONString(stringList.toArray()),CameraInfo.class);
        return liAnalysisService.findAnalysisResultByPersonId((String) (map.get("startTime")),
                                                                (String) (map.get("endTime")),
                                                                (Integer)(map.get("pageNum")),
                                                                (Integer)(map.get("pageSize")),
                                                                (String) (map.get("personId")),
                                                                cameraInfos);
    }
}
