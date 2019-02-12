package com.ferret.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ferret.bean.CameraInfo;
import com.ferret.bean.Cluster;
import com.ferret.bean.RealTimeImage;
import com.ferret.bean.Stranger;
import com.ferret.common.base.Common;
import com.ferret.dto.ClusterAdvanceDTO;
import com.ferret.dto.ClusterBrowseDTO;
import com.ferret.service.cluster.ClusterImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClusterImgController extends BaseController implements Common {

	@Autowired
	private ClusterImgService clusterImgService;

	private Map<String,Object> strangerParamsMap =new HashMap<>();

	/**
	 * @Descriptions 根据personId返回同行的所有人
	 * @Author xyc 2018/12/18
	 */
	@RequestMapping("/TXPerson")
	public List<Cluster> tongxingPerson(@RequestBody Map map) throws Exception {
		return clusterImgService.tongxingPerson((String)(map.get("startTime")),(String)(map.get("endTime")),
				(Integer) (map.get("minNum")),(String)(map.get("personId")));
	}

	// 根据页面的设置,选择的相机,实时陌生人的数量,更改后台的数据,后台使用此数据来进行实时陌生人的查询
	//如果前台不传递,则默认相机区域为空,查询条数为21
	@RequestMapping(value = "/setStrangerParams")
	public void setStrangerParams(@RequestBody Map map){
		List<String> stringList = (List<String>) map.get("cameraList");
		List<CameraInfo> cameraInfos = JSON.parseArray(JSONArray.toJSONString(stringList.toArray()),CameraInfo.class);
		strangerParamsMap.put("cameraList",cameraInfos);
		strangerParamsMap.put("pageSize",map.get("pageSize"));
		strangerParamsMap.put("enabled",map.get("enabled"));
	}

	//为了保证设置一次实时陌生人推送规则参数,可以一直使用,直到系统重启,所以将参数放置在后台(不支持不同用户的不同选择)

	/**
	 * y 0828
	 * @return
	 */
	@RequestMapping(value = "/getStrangerParams")
	public Map<String,Object> getStrangerParams(){
		if(strangerParamsMap.get("enabled") == null){
			strangerParamsMap.put("enabled",1);
		}
		return strangerParamsMap;
	}
	/**
	 * 获取jh_clusterpic表的最大id (陌生人查询用)
	 * 修改人:zwc
	 * 修改时间 2019/1/14
	 * 修改原因:重新处理了实时陌生人的逻辑
	 * @return
	 */
	@RequestMapping(value = "/getMaxId",method = RequestMethod.GET)
	public BigInteger getMaxId(){
		return clusterImgService.getMaxId();
	}
	/**
	 * 获取实时陌生人
	 * 修改人: zwc
	 * 修改时间 2019/1/14
	 * @Descriptions : 第一次获取cluster_pic表的最大id，一分钟后再获取该表的最大id，获取两个id之间的数据 并大于最小id的时间筛选数据
	 * @return
	 */
	@RequestMapping("/realTimeStranger")
	public Map realTimeStranger(BigInteger firstMaxId) {
		List<CameraInfo> cameraList= (List<CameraInfo>)strangerParamsMap.get("cameraList");
		BigInteger maxId = clusterImgService.getMaxId();
		List<Stranger> strangers = clusterImgService.realTimeStranger(firstMaxId,maxId,cameraList);
		// 如果从数据库中获取的数据为null,修改标记为false, 页面根据标记不更新数据
		if (strangers == null || strangers.size() <= 0) {
			strangerMap.put("flag", false);
		} else {
			// 标记为true,页面更新数据
			strangerMap.put("flag", true);
			strangerMap.put("strangers", strangers);
		}
		strangerMap.put("maxId",maxId);
		return strangerMap;
	}

	/** (弃用)
	 * 获取实时陌生人
	 * 修改人:y
	 * 修改时间0828
	 * 修改原因:重新处理了实时陌生人的逻辑
	 * @return
	 */
	@RequestMapping("/getStranger")
	@Deprecated
	public Map getStranger() {
		List<CameraInfo> cameraList= (List<CameraInfo>)strangerParamsMap.get("cameraList");
		List<Stranger> strangers = clusterImgService.getStranger(cameraList);
		// 如果从数据库中获取的数据为null,修改标记为false, 页面根据标记不更新数据
		if (strangers == null || strangers.size() <= 0) {
			strangerMap.put("flag", false);
		} else {
			// 标记为true,页面更新数据
			strangerMap.put("flag", true);
			for(int x=0;x<strangers.size();x++) {
				Stranger strangerResult = strangers.get(x);
				if(strangerResult.getCreateTime()!=null ) {
					strangerResult.setCreateTime(strangerResult.getCreateTime().substring(0, 19));
				}
			}
			strangerMap.put("strangers", strangers);
		}
		return strangerMap;
	}

	/**  (弃用)
	 * 获取最新的pageSize条tb_cluster里的数据
	 * 修改人: y
	 * 修改时间:0828
	 * 修改了逻辑,可根据相机列表区域, 查询最新的
	 * @return
	 */
	@RequestMapping("/getNewCluster")
	@Deprecated
	public List<Stranger> getNewCluster() {
		List<CameraInfo> cameraList= (List<CameraInfo>)strangerParamsMap.get("cameraList");
		Integer pageSize=(Integer)strangerParamsMap.get("pageSize");
		return clusterImgService.getNewCluster(cameraList, pageSize);
	}

	/**
	 * @Descriptions 陌生人查询
	 * @return
	 */
	@RequestMapping(value = "/getStrangerByCameraAndTime", method = RequestMethod.POST)
	public Map getStrangerByTimeAndCamera(@RequestBody Map map) {
		Integer totalNumber = (Integer)(map.get("totalNum"));
		Integer pageNum = (Integer)(map.get("pageNum"));
        Integer pageSize = (Integer)(map.get("pageSize"));
        if (pageSize == null) {
			pageSize = 18;
		}
		if (totalNumber == null) {
			totalNumber = 0;
		}
		List<String> stringList = (List<String>) map.get("cameraInfos");
		List<CameraInfo> cameraInfos = JSON.parseArray(JSONArray.toJSONString(stringList.toArray()),CameraInfo.class);
		Map resultMap = new HashMap();
		List<Stranger> result = clusterImgService.getStrangerByTimeAndCamera(
				(String)(map.get("startTime")),
				(String)(map.get("stime")),
				(String)(map.get("endTime")),
				pageNum,
				pageSize,
				cameraInfos);
        if (totalNumber < 1) {
			int totalNum = clusterImgService.getStrangerCountByTimeAndCamera(
					(String)(map.get("startTime")),
					(String)(map.get("stime")),
					(String)(map.get("endTime")),
					cameraInfos);
			resultMap.put("total", totalNum);
        } else {
           resultMap.put("total", totalNumber);
        }
		resultMap.put("resultList", result);
		// 返回页面,供下一步查询使用
		resultMap.put("stime", (String)(map.get("stime")));
		// 如果页面返回了总条数并且是第一页,说明是第一次查询,查询总条数,否则不查询总条数
		return resultMap;
	}

	/**
	 * @Descriptions 陌生人查询（根据personID查询详细陌生人信息）
	 * @return
	 */
	@RequestMapping("/getStrangerByPersonId")
	public Map getStrangerByPersonId(@RequestBody Map map) {
		List<String> stringList = (List<String>) map.get("cameraInfos");
		Integer pageSize = (Integer) map.get("pageSize");
		if (pageSize == null) {
			pageSize = 10;
		}
		List<CameraInfo> cameraInfos = JSON.parseArray(JSONArray.toJSONString(stringList.toArray()),CameraInfo.class);
		Map resultMap = new HashMap<>();
		List<Stranger> result = clusterImgService.getStrangerByPersonId(
				(String)(map.get("startTime")),
				(String)(map.get("stime")),
				(String)(map.get("endTime")),
				(Integer)(map.get("pageNum")),
				pageSize,
				cameraInfos,
				(String)(map.get("personId"))
		);
		resultMap.put("resultList", result);
		return resultMap;
	}

	/**
	 * @Description 根据用户点击的摄像头,查询数据库,获取抓拍图片
	 * @param cameraIp 相机IP
	 * @param maxID    最大的ID
	 * @date 2019-01-02 13:25:27
	 * @author xieyingchao
	 */
	@RequestMapping("/getMonitorImage")
	public Map getMonitorImage(String cameraIp, Integer maxID) {
		// 根据相机ip,去数据库中查询对应的图片信息,返回
		// 用户第一次点击这个摄像头
		if (maxID < 1) {
			int maxIDResult = clusterImgService.getMaxImageIDByCameraIp();
			Map resultMap = new HashMap<>();
			if(maxIDResult>0){
				resultMap.put("maxID", maxIDResult);
			}
			return resultMap;
		}
		// 否则是正常的查询
		List<RealTimeImage> resultList = clusterImgService.getMonitorImageByCameraIp(cameraIp,maxID);
		if (resultList != null && resultList.size() > 0) {
			// 获取最大的maxID,覆盖掉页面原本的maxID
			int maxIDResult = resultList.get(resultList.size() - 1).getID();
			Map resultMap = new HashMap<>();
			resultMap.put("maxID", maxIDResult);
			resultMap.put("listData", resultList);
			return resultMap;
		}
		return null;
	}

	/**
	 * @Descriptions 根据地区码,开始时间,结束时间查询出现的所有人,以及每个人出现的次数
	 * @param requestMap
	 * @return
	 */
	@RequestMapping("/getPersonByTimeAndArea")
	public Map getPersonByTimeAndArea(@RequestBody Map requestMap){
		String startTime = (String)requestMap.get("startTime");
		String endTime = (String)requestMap.get("endTime");
		List<String> areaCodes = (List<String>)requestMap.get("areaCodes");
		//根据相机ip,起始时间段查询出符合要求的数据
		List<ClusterAdvanceDTO> getPersonByTimeAndArea = clusterImgService.getPersonByTimeAndArea(startTime,endTime,areaCodes);
		if(getPersonByTimeAndArea !=null && getPersonByTimeAndArea.size()>0 && areaCodes !=null){
			Map<String,List<ClusterAdvanceDTO>> personIdSortMap = new HashMap();
			// 以下代码为获取每个person出现的次数, 以及按照personId分类,存入map,并将count相加
			for(ClusterAdvanceDTO c : getPersonByTimeAndArea){
				List<ClusterAdvanceDTO> clusterPasses = personIdSortMap.get(c.getPersonId());
				// clusterDates为null, 说明是第一次出现放入对应的map中
				if(clusterPasses == null ){
					List<ClusterAdvanceDTO> list = new ArrayList<>();
					list.add(c);
					// 按照personId分类,放入分类的map中
					personIdSortMap.put(c.getPersonId(),list);
				}else {
					// 不是第一次出现, 将出现的这条记录放入分类map集合中,并将第一条数据的count改为所有count相加  count:当前personId在某个摄像头下出现的次数
					List<ClusterAdvanceDTO> personClusterPasses = personIdSortMap.get(c.getPersonId());
					// 放入最后,方便过滤使用
					personClusterPasses.add(c);
					// 将count全部相加,将第一个count设置为此值
					int totalCount =0;
					for(ClusterAdvanceDTO cc : personClusterPasses){
						totalCount += cc.getCount();
					}
					personClusterPasses.get(0).setCount(totalCount);
					personIdSortMap.put(c.getPersonId(), personClusterPasses);
				}
			}
			List<ClusterAdvanceDTO> resultList = new ArrayList<>();
			//循环遍历分类后的person, 符合要求的,放入resultList中
			for(String personId : personIdSortMap.keySet()) {
				List<ClusterAdvanceDTO> clusterPasses = personIdSortMap.get(personId);
				int flage = 0;
				for (String areaCode : areaCodes) {
					for (int x = 0; x < clusterPasses.size(); x++) {
						ClusterAdvanceDTO clusterPass = clusterPasses.get(x);
						if (clusterPass.getNumber().startsWith("C" + areaCode)) {
							flage++;
							break;
						}
					}
				}
				if(flage == areaCodes.size()){
					resultList.add(clusterPasses.get(0));
				}
			}
			Map returnMap = new HashMap();
			returnMap.put("totalNum",resultList.size());
			returnMap.put("resultList",resultList);
			return  returnMap;
		}
		return  null;
	}

	/**
	 * 根据personid来查询人员的轨迹
	 * @param requestMap
	 * @return
	 */
	@RequestMapping("/getPersonTrajectory")
	public List<ClusterBrowseDTO> getPersonTrajectory(@RequestBody Map requestMap){
		String startTime = (String)requestMap.get("startTime");
		String endTime = (String)requestMap.get("endTime");
		String personId = (String)requestMap.get("personId");
		List<String> areaCodes = (List<String>)requestMap.get("areaCodes");
		return clusterImgService.getPersonTrajectory(startTime,endTime,personId,areaCodes);
	}
}
