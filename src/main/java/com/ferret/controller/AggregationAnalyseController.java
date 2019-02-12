package com.ferret.controller;

import com.ferret.bean.AggregationAnalyse;
import com.ferret.service.analyse.AggregationAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 聚集点分析
 * @since  2019/1/19
 * @description 根据所选时间段,所选相机  统计jh_alarm表中 卡口，人数(出现的总人数)，次数(出现的总次数)
 * @author zwc
 */
@RestController
public class AggregationAnalyseController extends BaseController{

    @Autowired
    private AggregationAnalyseService aggregationAnalyseService;

    @PostMapping("/aggregationAnalyse")
    public List<AggregationAnalyse> aggregationAnalyse(@RequestBody Map map){
        ArrayList<String> cameraIds = (ArrayList<String>) map.get("cameraIds");
        String beginTime = (String) map.get("beginTime");
        String endTime = (String) map.get("endTime");
        return aggregationAnalyseService.aggregationAnalyse(cameraIds,beginTime,endTime);
    }
}
