package com.ferret.controller;

import com.ferret.bean.AllopatryAnalyse;
import com.ferret.bean.CameraInfo;
import com.ferret.service.analyse.AllopatryAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 区域分析 (查找   不同区域不同时间  出现的同一个人)
 *  2018-7-17
 * @author zwc
 */
@RestController
public class AllopatryAnalyseController extends BaseController{

	@Autowired
	private AllopatryAnalyseService allopatryAnalyseservice;
	/**
	 *区域分析（获取2个时间地点 出现的同一个人）
	 * @return 
	 */
	@RequestMapping(value="/allopatryAnalyse_old",method=RequestMethod.POST)
	public Map<String, Object> getAllopatryAnalyse(@RequestBody AllopatryAnalyse allopatryAnalyse) {
		
		return allopatryAnalyseservice.getAllopatryAnalyse(allopatryAnalyse);
		
	}

	/**
	 *区域分析（获取2个时间地点 出现的同一个人） 新
	 * @return
	 */
	@RequestMapping(value="/allopatryAnalyseNew",method=RequestMethod.POST)
	public Map<String, Object> getAllopatryAnalyseNew(@RequestBody Map<String,Object> param) {
		String beginTimeA = (String) param.get("beginTimeA");
        String endTimeA = (String) param.get("endTimeA");
        ArrayList<String> cameraNumbersListA = (ArrayList<String>) param.get("cameraNumbersListA");
        String beginTimeB = (String) param.get("beginTimeB");
        String endTimeB = (String) param.get("endTimeB");
        ArrayList<String> cameraNumbersListB = (ArrayList<String>) param.get("cameraNumbersListB");

        return allopatryAnalyseservice.getAllopatryAnalyse(beginTimeA,endTimeA, cameraNumbersListA,
                beginTimeB,endTimeB,cameraNumbersListB );
	}
    /**
     *区域分析（该personId符合A条件，或者 符合B条件的数据）
     * @return
     */
    @RequestMapping(value="/allopatryPersonInfo",method=RequestMethod.POST)
    public Map<String,Object> allopatryPersonInfo(@RequestBody Map<String,Object> param) {
        String personId = (String) param.get("personId");
        String beginTimeA = (String) param.get("beginTimeA");
        String endTimeA = (String) param.get("endTimeA");
        ArrayList<String> cameraNumbersListA = (ArrayList<String>) param.get("cameraNumbersListA");
        String beginTimeB = (String) param.get("beginTimeB");
        String endTimeB = (String) param.get("endTimeB");
        ArrayList<String> cameraNumbersListB = (ArrayList<String>) param.get("cameraNumbersListB");
        Integer pageNum = (Integer) param.get("pageNum");
        Integer pageSize = (Integer) param.get("pageSize");
        Integer total = (Integer) param.get("total");

        return allopatryAnalyseservice.getAllopatryPersonInfo(personId,beginTimeA,endTimeA, cameraNumbersListA,
                beginTimeB,endTimeB, cameraNumbersListB,pageNum,pageSize,total);
    }
	
	/**
	 * 获取相机列表
	 * @param cameraNumber
	 * @return 
	 * 修改人:y
	 * 修改时间:0720
	 * 修改原因:将CameraData删除,改为返回CameraInfo
	 */
	@RequestMapping(value="/getCameraList",method=RequestMethod.POST)
	public List<CameraInfo> getCameraList(@RequestBody List<String> cameraNumber) {
		if(cameraNumber!=null && cameraNumber.size()<1){
			return null;
		}
		return allopatryAnalyseservice.getCameraList(cameraNumber);
	}
}
