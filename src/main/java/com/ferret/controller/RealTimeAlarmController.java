package com.ferret.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.AlarmCount;
import com.ferret.bean.ClusterPass;
import com.ferret.bean.RealTimeAlarm;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.bean.pagebean.RealTimeAlarmPage;
import com.ferret.dto.AlarmPersonDTO;
import com.ferret.dto.AlarmQueryDTO;
import com.ferret.dto.PageDTO;
import com.ferret.service.alarm.AlarmService;
import com.ferret.service.camera.cameraInfo.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.controller;
 * @auth: cc;
 * @since: 2017/12/15 0015;
 * @desc:
 *
 * 修改: y
 * 时间: 1221
 * 所有的没有使用,都是建立在目前报警查询部分功能没有发现使用的基础上
 */
@RestController
public class RealTimeAlarmController extends BaseController {
    @Autowired
    private AlarmService alarmService;

    @Autowired
    private CameraInfoService cameraInfoService;
    /**
     * 查询历史报警所有信息
     *
     * @return
     *
     *
     * restApi中有此接口, 但页面没有调用此接口  y 1221
     */
    @Deprecated
    @RequestMapping(value = "/alarms", method = RequestMethod.GET)
    public List<RealTimeAlarm> doFindAllObject() {
        return alarmService.findAllAlarm();
    }

    /**
     * 根据条件查询/分页查询
     *
     * @return
     *
     * restApi中有此接口, 但页面没有调用此接口 y 1221
     */
    @Deprecated
    @RequestMapping(value = "/alarms/findAlarm", method = RequestMethod.POST)
    public RealTimeAlarmPage doFindByBk(@RequestBody RealTimeAlarmPage alarmPage) {
        alarmPage = alarmService.findByBk(alarmPage);
        return alarmPage;
    }


    /**
     * 根据布控id查询报警记录并分页
     *
     * @param pageNum  分页
     * @param pageSize 页大小
     * @author cc
     * @return
     * restApi 中有此接口, 但没有页面使用  y  1221
     */
    @RequestMapping(value = "/alarms/{bkId}/page", method = RequestMethod.GET)
    public ResponseEntity listAlarmsByBkId(@PathVariable("bkId") Integer bkId, Integer pageNum, Integer pageSize) {
        PageDTO pageDTO = alarmService.listAlarmsByBkId(bkId, pageNum, pageSize);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * @Description 查询报警信息
     * @param alarmQueryDTO
     * @date 2019-01-04 12:34:40
     * @author xieyingchao
     */
    @RequestMapping(value = "/alarms/search",method = RequestMethod.POST)
    public ResponseEntity listAlarmsBySearch(@RequestBody AlarmQueryDTO alarmQueryDTO){
        PageDTO<RealTimeAlarm> pageDTO = alarmService.listAlarmsByQueryDTO(alarmQueryDTO);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * @Description 报警信息归档
     * @param queryDtoStr 
     * @date 2019-01-04 12:35:16
     * @author xieyingchao
     */
    @RequestMapping(value = "/alarms/bkId",method = RequestMethod.GET)
    public ResponseEntity listBKIdsByQueryDTO(@RequestParam String queryDtoStr){
        AlarmQueryDTO alarmQueryDTO = JSON.parseObject(queryDtoStr,AlarmQueryDTO.class);
        List<Map<Integer,Integer>> list = alarmService.listBKIdsByQueryDTO(alarmQueryDTO);
        return ResponseEntity.ok(list);
    }

    /**
     * 更新数据
     *
     * @param status
     * @param confirmTime
     * @param userId
     * @param sampleFlag
     *
     * 此方法没有使用 restApi中都没有    y 1221
     */
    @Deprecated
    @RequestMapping(value = "/alarms/updateAlarm", method = RequestMethod.PUT)
    public void doUpdateByStatus(@RequestParam Integer status,
                                 @RequestParam Date confirmTime,
                                 @RequestParam Integer userId,
                                 @RequestParam Integer sampleFlag) {
        alarmService.updateByStatus(status, confirmTime, userId, sampleFlag);

    }

    /**
     * 根据报警id,更新报警图片的样本信息
     *
     * @param sampleFlag AB样本,1,表示A样本,2表示B样本
     *
     * restApi中有此接口 WarningCard中有调用此接口的方法,但并未使用,使用的是changeStatus   y 1221
     */
    @Deprecated
    @RequestMapping(value = "/alarms/{id}", method = RequestMethod.PATCH)
    public ResponseEntity doUpdateBySampleFlag(@PathVariable("id") Integer alarmId, Integer sampleFlag) {
        alarmService.updateBySampleFlag(alarmId, sampleFlag);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * @Description 修改报警状态为正报 (报警查询页面调用,调用页面:WarningCard.vue)
     * @param id
     * @date 2019-01-04 15:30:38
     * @author xieyingchao
     */
    @RequestMapping(value = "/changestatus/{id}")
    public String changestatus(@PathVariable("id") BigInteger id) {
    	Integer result= alarmService.changeStatusById(id);
    	String message="false";
    	if(result>0) {
    		message="true";
    	}
        return message;
    }

    /**
     * @Description 根据报警信息表查询布控对象的所有信息以及一分钟内出现的报警信息
     * @param id jh_alarm表中主键的id
     * @param alarmTime
     * @date 2019-01-04 12:58:42
     * @author xieyingchao
     */
    @RequestMapping(value = "/alarms/findBkMessage", method = RequestMethod.GET)
    public NewBuKong findBKbyRealTimeAlarmUUId(BigInteger id, String alarmTime) {
       return alarmService.findBKbyRealTimeAlarmId(id,alarmTime);
    }
    /**
     * 根据前台传递过来的websocket接受的报警信息中的报警id查询bk信息,并返回最近的alarmSize条报警信息
     * (websocket有报警信息返回时,调用,调用页面:main.vue)
     *  此处使用了报警表中的布控图片,因为不需要关联查询布控表,所以在service层对于图片路径的处理会和getReamTimeAlarmByAlarmId不同
     *  y  0108
     * @param id  报警表中的自增id, 不是报警id
     * @param alarmSize 要返回几条报警信息
     * @return
     */
    @RequestMapping(value = "/alarms/getRealTimeAlarm")
    public List<RealTimeAlarm> getRealTimeAlarm(String id,Integer alarmSize){
        if(null == alarmSize){
            alarmSize = 5;
        }
        return alarmService.findAlarmById(id,alarmSize);
    }

    /**
     * 查询最近报警的peopleSize个布控对象,并且这几个个布控对象最近的alarmSize次报警(实时报警页面调用 realTimeWarning.vue)  y 1221
     * @return
     */
    @RequestMapping(value = "/alarms/findFivePeopleRealTimeAlarm",method = RequestMethod.GET)
    public  List<List<RealTimeAlarm>> findFivePeopleRealTimeAlarm(Integer peopleSize,Integer alarmSize){
        if(null == peopleSize){
            peopleSize = 5;
        }
        if(null == alarmSize){
            alarmSize =5;
        }
        return alarmService.findPeopleRealTimeAlarm(peopleSize,alarmSize);
    }

    /**
     * 统计时间段内各个布控分组的报警数量
     * @return string
     * @author zwc 2018/10/12
     */
    @Deprecated
    @RequestMapping(value = "/getAlarmCount",method = RequestMethod.POST)
    public List<AlarmCount> getAlarmNumByBuKongGroup(@RequestBody ClusterPass clusterPass) {
        return alarmService.getAlarmNumByBuKongGroup(clusterPass);
    }
    /**
     * 统计时间段内各个布控分组的报警数量
     * @return string
     * @author zwc 2018/10/12
     */
//    @RequestMapping(value = "/getAlarmCount",method = RequestMethod.POST)
    public List<AlarmCount> getAlarmNumByBuKongGroup(@RequestBody Map map) {
        return alarmService.getAlarmNumByBuKongGroup((String)(map.get("startTime")),(String)(map.get("endTime")));
    }

    /**
     * 统计时间段内各个布控分组的报警信息
     * @return List
     * @author hyl 2018/10/12
     */
    @Deprecated
    @RequestMapping(value = "/getAlarmInfo",method = RequestMethod.POST)
    public List<RealTimeAlarm> getAlarmInfoByBuKongGroup(@RequestBody AlarmPersonDTO alarmPersonDTO) {
        return alarmService.getAlarmInfoByBuKongGroup(alarmPersonDTO);
    }
    /**
     * @Description 统计时间段内各个布控分组的报警信息
     * @return List
     * @author xyc 2018/10/12
     */
//    @RequestMapping(value = "/getAlarmInfo",method = RequestMethod.POST)
    public List<RealTimeAlarm> getAlarmInfoByBuKongGroup(@RequestBody Map map) {
        // 获取到布控分组信息
        List<String> stringList = (List<String>) map.get("groupIds");
        List<AlarmCount> alarmCounts = JSON.parseArray(JSONObject.toJSONString(stringList.toArray()),AlarmCount.class);
        return alarmService.getAlarmInfoByBuKongGroup((String)(map.get("beginTime")),(String)(map.get("endTime")),alarmCounts);
    }
    /**
     * @Descriptions 统计总报警次数  统计今日报警次数
     * @Author xyc
     * @Data 2018-12-10
     */
    @RequestMapping(value = "/getAlarmCountForBigData",method = RequestMethod.GET)
    public Map getAlarmCount(){
        return alarmService.getAlarmCount();
    }
}
