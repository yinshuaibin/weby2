package com.ferret.controller;

import com.ferret.bean.User;
import com.ferret.service.statistics.BigDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataStatisticsController extends BaseController{

	@Autowired
    private BigDataService bigDataService;

	/**
	 * @Description 获取大数据页面数据（抓拍数、报警数、用户数、相机数）
	 * @param req
	 * @date 2019-01-11 13:05:49
	 * @author xieyingchao
	 */
	@RequestMapping(value = "/test/getAllCountforBigData", method = RequestMethod.GET)
	public ResponseEntity getCountForBigData(HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        Assert.notNull(user,"用户登录超时或者后台服务已重启,请重新登录!");
        Map map = new HashMap();
        int snapsTotalNum = bigDataService.getTotalSnapshotCount();
        int snapsTodayNum = bigDataService.getTodaySnapshotCount();
        // 获取抓拍总数
        map.put("snapshotTotalNum", (snapsTodayNum+snapsTotalNum));
        // 获取今日抓拍数
        map.put("snapshotTodayNum", snapsTodayNum);
        // 获取总报警数
        map.put("alarmTotalNum", bigDataService.getTotalAlarmCount());
        // 获取今日报警数
        map.put("alarmTodayNum", bigDataService.getTodayAlarmCount());
        // 获取在线相机数
        map.put("onlineCamera", bigDataService.getOnLineCameraCount(user.getAreaId()));
        // 获取总用户数
        map.put("allUser", bigDataService.getTotalUserCount());
        map.put("clusterTotalNum", bigDataService.getTotalClusterCount());
        map.put("clusterTodayNum", bigDataService.getTodayClusterCount());
        return ResponseEntity.ok(map);
    }
    
    /**
     * @Description 获取异常相机列表
     * @param req 
     * @date 2019-01-11 13:18:41
     * @author xieyingchao
     */
    @RequestMapping(value = "/test/getOFFCameraList", method = RequestMethod.GET)
    public ResponseEntity getOffCamera(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        Assert.notNull(user,"用户登录超时或者后台服务已重启,请重新登录!");
        List<String> resultList =  bigDataService.getOffLineCameraName(user.getAreaId());
        return ResponseEntity.ok(resultList);
    }
    /**
     * @Description 查询历史人数统计 今天，昨天，本周，上周，本月，上月 新进人数
     * @date 2019-01-11 14:31:48
     * @author xieyingchao
     */
    @RequestMapping(value="/test/bigdatas", method=RequestMethod.GET)
    public ResponseEntity getHistoryBigdata() {
        //（今天，昨天，本周，上周，本月，上月 新进人数）
        ArrayList<Integer> historyBigdata = bigDataService.getHistoryBigdata();
        return ResponseEntity.ok(historyBigdata);
    }

    /**
     * @Description 查询今年截至目前每个月（包括本月），月新进人数
     * @date 2019-01-11 16:16:01
     * @author xieyingchao
     */
    @RequestMapping(value="/test/bigdatas/permonth", method=RequestMethod.GET)
    public ResponseEntity getPerMonthBigdata() {
        List<Integer> result = bigDataService.getPerMonthBigdata();
        return ResponseEntity.ok(result);
    }

    /**
     * @Description 查询今年每月总人数
     * @date 2019-01-11 16:22:13
     * @author xieyingchao
     */
    @RequestMapping(value="/test/bigdatas/permonthtotal", method=RequestMethod.GET)
    public ResponseEntity getPermonthTotal() {
        List<Integer> result = bigDataService.getPerMonthTotal();
        return ResponseEntity.ok(result);
    }

    /**
     * @Description 获取所有（历史所有）日人数(以及当天的日期)
     * @date 2019-01-11 16:33:29
     * @author xieyingchao
     */
    @RequestMapping(value="/test/bigdatas/allPer", method=RequestMethod.GET)
    public ResponseEntity getDateChart() {
        Map<String, Object> resultMap = bigDataService.getAllPersonCount();
        return ResponseEntity.ok(resultMap);
    }

    /**
     * @Description 获取所有报警（历史所有）日人数(以及当天的日期)
     * @date 2019-01-11 16:56:40
     * @author xieyingchao
     */
    @RequestMapping(value="/test/bigdatas/alarmPer", method=RequestMethod.GET)
    public ResponseEntity getAlarmPerson() {
        Map<String, Object> resultMap = bigDataService.getAlarmPerson();
        return ResponseEntity.ok(resultMap);
    }

	/**
	 * 查询聚类总人数,今日聚类人数
	 * @return
	 */
	@RequestMapping("/getAllClusterCountAndDayCount")
	public Map<String,Integer> getAllClusterCountAndDayCount(){
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("allClusterCount", bigDataService.getTotalClusterCount());
		resultMap.put("dayClusterCount", bigDataService.getTodayClusterCount());
		return resultMap;
	}

	
}
