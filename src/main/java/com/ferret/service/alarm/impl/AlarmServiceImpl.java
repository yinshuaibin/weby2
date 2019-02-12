package com.ferret.service.alarm.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.ferret.bean.AlarmCount;
import com.ferret.bean.AlarmInterface;
import com.ferret.bean.ClusterPass;
import com.ferret.dao.AlarmCountMapper;
import com.ferret.dto.AlarmPersonDTO;
import com.ferret.service.alarm.AlarmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ferret.bean.RealTimeAlarm;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.bean.pagebean.RealTimeAlarmPage;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.RealTimeAlarmMapper;
import com.ferret.dto.AlarmQueryDTO;
import com.ferret.dto.PageDTO;
import com.ferret.utils.ImagePrefixProperties;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 历史报警信息业务层
 *
 * @pack: com.ferret.service.alarm.impl;
 * @auth: cc;
 * @since: 2017/12/15;
 * @desc:
 */

@Service
public class AlarmServiceImpl implements AlarmService {

	private static final String DATE_FMT = "yyyy-MM-dd HH:mm:ss";
	@Resource
	private RealTimeAlarmMapper realTimeAlarmMapper;

	@Autowired
	private CameraInfoMapper cameraMapper;

	@Autowired
	private ImagePrefixProperties imagePrefixProperties;

	/**
	 * 查询所有报警信息
	 *
	 * @return
	 */
	@Override
	public List<RealTimeAlarm> findAllAlarm() {
		List<RealTimeAlarm> realTimeAlarmList = realTimeAlarmMapper.findAllAlarm();
		return realTimeAlarmList;
	}
	/**
	 * 更新数据sampleFlag
	 *
	 * @param sampleFlag
	 */
	@Override
	public void updateBySampleFlag(Integer alarmId, Integer sampleFlag) {
		Assert.notNull(alarmId, "更新记录id参数不能为空");
		realTimeAlarmMapper.updateBySampleFlag(alarmId, sampleFlag);
	}

	/**
	 * 更新数据
	 *
	 * @param status
	 * @param confirmTime
	 * @param userId
	 * @param sampleFlag
	 */

	@Override
	public void updateByStatus(Integer status, Date confirmTime, Integer userId, Integer sampleFlag) {

		realTimeAlarmMapper.updateByStatus(status, confirmTime, userId, sampleFlag);
	}
	@Override
	public RealTimeAlarmPage findByBk(RealTimeAlarmPage alarmPage) {
		StringBuffer nums = new StringBuffer();
		StringBuffer like = new StringBuffer();
		// ------处理number和id的对应---------------------------
		for (String num : alarmPage.getCameraNumbers()) {
			if (num.length() == 19) {
				nums.append("'").append(num).append("'").append(",");
			}
			if (num.length() < 19) {
				like.append("number like '").append(num).append("%' or ");
			}
		}
		if (nums.length() > 0)
			nums.deleteCharAt(nums.lastIndexOf(","));
		if (like.length() > 0)
			like.delete(like.lastIndexOf("or"), like.lastIndexOf("or") + 2);
		List<Integer> ids = cameraMapper.number2Id(nums.toString(), like.toString());
		nums.setLength(0);
		for (int i = 0; i < ids.size(); i++) {
			nums.append(ids.get(i).toString()).append(",");
		}
		nums.deleteCharAt(nums.lastIndexOf(","));
		Map map = new HashMap();
		map.put("ids", nums.toString());
		map.put("alarmPage", alarmPage);
		alarmPage.setDataCount(realTimeAlarmMapper.alarmQueryCount(map));
		alarmPage.setListData(realTimeAlarmMapper.alarmQuery(map));
		return alarmPage;
	}

	@Override
	public List<RealTimeAlarm> findAlarmByCameras(List<String> cameraIds, Date startTime, Date endTime,Integer pageNum,Integer pageSize) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if( null !=startTime && null != endTime){
        	Integer startNum = (pageNum -1) * pageSize;
            return realTimeAlarmMapper.findAlarmByCameraIds(format.format(startTime), format.format(endTime),cameraIds,startNum,pageSize);
        }
		return null;
	}

	@Override
	public Integer findAlarmByCamerasCount(List<String> cameraIds, Date startTime, Date endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if( null !=startTime && null != endTime){
			return realTimeAlarmMapper.findAlarmByCameraIdsCount(format.format(startTime), format.format(endTime),cameraIds);
		}
		return 0;
	}


	@Override
	public PageDTO listAlarmsByBkId(Integer bkId, Integer pageNum, Integer pageSize) {
		Page<RealTimeAlarm> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> {
			realTimeAlarmMapper.listAlarmsByBkId(bkId);
		});
		PageDTO<RealTimeAlarm> pageDTO = new PageDTO<>();
		pageDTO.setList(page.getResult()).setTotal(page.getTotal());
		return pageDTO;
	}

	@Override
	public List<RealTimeAlarm> findAlarmByBukongId(int bukongId, Date startTime, Date endTime,Integer pageNum, Integer pageSize) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if( null !=startTime && null != endTime){
			Integer startNum = (pageNum -1) * pageSize;
            return realTimeAlarmMapper.findAlarmByBukongId(format.format(startTime), format.format(endTime),bukongId,startNum,pageSize);
        }
		return null;
	}

	@Override
	public Integer findAlarmByBukongIdCount(int bukongId, Date startTime, Date endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if( null !=startTime && null != endTime){
			return realTimeAlarmMapper.findAlarmByBukongIdCount(format.format(startTime), format.format(endTime),bukongId);
		}
		return 0;
	}

	@Override
	public PageDTO listAlarmsByQueryDTO(AlarmQueryDTO alarmQueryDTO) {
		Page<RealTimeAlarm> page = PageHelper.startPage(alarmQueryDTO.getPageNum(), alarmQueryDTO.getPageSize())
				.doSelectPage(() -> {
			realTimeAlarmMapper.listByQueryDTO(alarmQueryDTO);
		});
		List<RealTimeAlarm> realTimeAlarmList = page.getResult();
		List<RealTimeAlarm> result = new ArrayList<>();
		for (RealTimeAlarm realTimeAlarm:realTimeAlarmList) {
		    if(null != realTimeAlarm.getAlarmImagePath()) {
                realTimeAlarm.setAlarmImagePath(imagePrefixProperties.getAlarmImageUrl() + realTimeAlarm.getAlarmImagePath());
            }
            if(null != realTimeAlarm.getBackgroundImagePath()) {
                realTimeAlarm.setBackgroundImagePath(imagePrefixProperties.getAlarmImageUrl() + realTimeAlarm.getBackgroundImagePath());
            }
            if(null != realTimeAlarm.getPeoplepicpath()) {
                realTimeAlarm.setPeoplepicpath(imagePrefixProperties.getAlarmImageUrl() + realTimeAlarm.getPeoplepicpath());
            }
            if(null != realTimeAlarm.getBkImagePath()) {
                realTimeAlarm.setBkImagePath(imagePrefixProperties.getBukongImageUrl() + realTimeAlarm.getBkImagePath());
            }
				result.add(realTimeAlarm);
		}
		PageDTO<RealTimeAlarm> pageDTO = new PageDTO<>();
		pageDTO.setList(result).setTotal(page.getTotal());
		return pageDTO;
		}

	@Override
	public List<Map<Integer, Integer>> listBKIdsByQueryDTO(AlarmQueryDTO alarmQueryDTO) {
		return realTimeAlarmMapper.listBKIdsByQueryDTO(alarmQueryDTO);
	}
    /**
     * @Description 修改报警状态为正报
     * @param AlarmId
     * @date 2019-01-04 15:34:38
     * @author xieyingchao
     */
	@Override
	public Integer changeStatusById(BigInteger AlarmId) {

		return realTimeAlarmMapper.updateByAlarmId(AlarmId);
	}

	/**
	 * @Description 跟据报警信息查询最近一分钟的报警信息
	 * @param id
	 * @param alarmTime
	 * @date 2019-01-04 14:01:06
	 * @author xieyingchao
	 */
	@Override
	public NewBuKong findBKbyRealTimeAlarmId(BigInteger id, String alarmTime) {
		NewBuKong bk = realTimeAlarmMapper.findBKbyRealTimeAlarmId(id);
		List<RealTimeAlarm> result = new ArrayList<>();
		//处理布控图片,手动替换为url路径
		if(null != bk.getImagePath()) {
			List<String> imagePaths = bk.getImagePath();
			if (imagePaths != null && imagePaths.size() > 0) {
				for (int x = 0; x < imagePaths.size(); x++) {
					String imagePath = imagePaths.get(x);
					imagePath = imagePrefixProperties.getBukongImageUrl() + imagePath;
					imagePaths.set(x, imagePath);
				}
				bk.setImagePath(imagePaths);
			}
		}
		//设置该布控对象一分钟内报警的信息
        List<RealTimeAlarm> realTimeAlarms = realTimeAlarmMapper.alarmByMinuteTime(id,alarmTime.substring(0, 16)+"%");
        for (RealTimeAlarm realTimeAlarm:realTimeAlarms) {
            if(null != realTimeAlarm.getAlarmImagePath()) {
                realTimeAlarm.setAlarmImagePath(imagePrefixProperties.getAlarmImageUrl() + realTimeAlarm.getAlarmImagePath());
            }
            if(null != realTimeAlarm.getPeoplepicpath()) {
                realTimeAlarm.setPeoplepicpath(imagePrefixProperties.getAlarmImageUrl() + realTimeAlarm.getPeoplepicpath());
            }
            result.add(realTimeAlarm);
        }
		bk.setRealTimeAlarms(result);
		return bk;
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
	@Override
	public List<RealTimeAlarm> findAlarmById(String id,Integer alarmSize) {
		List<RealTimeAlarm> result=new ArrayList<>();
		if(StringUtils.isNotBlank(id)){
			//根据报警表的自增id,查询该报警人的最近5条报警数据
			result= realTimeAlarmMapper.findAlarmById(id,alarmSize);
		}
		if(result.size()>0) {
			//拿到最新的一条报警数据,第一条报警数据前台用来展示布控的信息,此处将处理
			RealTimeAlarm alarm = result.get(0);
            RealTimeAlarm oneAlarm = (RealTimeAlarm) alarm.clone();
            for (int x = 0; x < result.size(); x++) {
				RealTimeAlarm c = result.get(x);
				// 增加标记, true为展示报警信息
				c.setFlage(true);
                // 处理报警图片和布控图片路径问题
                c.setAlarmImagePath(imagePrefixProperties.getAlarmImageUrl()+c.getAlarmImagePath());
                c.setBkImagePath(imagePrefixProperties.getAlarmImageUrl()+c.getBkImagePath());
                // 处理时间多.0问题
                c.setAlarmTime(c.getAlarmTime().substring(0,19));
            }
			// 将克隆的第一条数据放在结果集中第一位,用来展示布控信息,处理布控图片路径
            oneAlarm.setBkImagePath(imagePrefixProperties.getAlarmImageUrl()+oneAlarm.getBkImagePath());
            result.add(0,oneAlarm);
		}
		return result;
	}

	/**
	 * 查询数据库中最近报警的peopleSize个人的最新alarmSize条报警记录
	 * @param peopleSize 最近报警的人数
	 * @param alarmSize  每个报警的人的最近几条报警记录
	 * @return
	 * y 1220
	 */
	@Override
	public List<List<RealTimeAlarm>> findPeopleRealTimeAlarm(Integer peopleSize,Integer alarmSize) {
	    //获取符合条件的alarmid集合,格式为:[1,2,3, 8,5,4, 10,9,123]
		List<String> alarmIds = realTimeAlarmMapper.findAlarmId(peopleSize);
		//拿到所有符合条件的集合
        List<RealTimeAlarm> alarms = realTimeAlarmMapper.findPeopleRealTimeAlarm(alarmIds,alarmSize);
        //返回结果集
        List<List<RealTimeAlarm>> result=new ArrayList<>();
        //将每个bkId对应的报警信息,放入一个list集合中,每个list集合有alarmSize条数据,第一条数据为最新一条报警信息,通过拷贝,放入list集合第一位,
        // 设置标记flage为flase用来展示布控信息
        Set<Integer> ids=new HashSet<>();
        for(RealTimeAlarm realTimeAlarm:alarms){
            Integer bkId = realTimeAlarm.getBkId().intValue();
            if(StringUtils.isNotBlank(realTimeAlarm.getAlarmTime())){
                realTimeAlarm.setAlarmTime(realTimeAlarm.getAlarmTime().substring(0,19));
                realTimeAlarm.setAlarmImagePath(imagePrefixProperties.getAlarmImageUrl()+realTimeAlarm.getAlarmImagePath());
                realTimeAlarm.setBkImagePath(imagePrefixProperties.getAlarmImageUrl()+realTimeAlarm.getBkImagePath());
				realTimeAlarm.setBackgroundImagePath(imagePrefixProperties.getAlarmImageUrl() + realTimeAlarm.getBackgroundImagePath());
			}
            realTimeAlarm.setFlage(true);
            if(ids.size()<1 || !ids.contains(bkId)){
                List<RealTimeAlarm> as=new ArrayList<>();
                RealTimeAlarm realTimeAlarmOne=(RealTimeAlarm)realTimeAlarm.clone();
                realTimeAlarmOne.setFlage(false);
                as.add(0,realTimeAlarmOne);
                for( RealTimeAlarm r:alarms){
                    Integer bkId1 = r.getBkId().intValue();
                    //integer 类型的数据如果使用== 比较 则只能比较-128->127的数据
                    if(bkId1.equals(bkId)){
                        as.add(r);
                    }
                }
                result.add(as);
            }
            ids.add(bkId);
        }
        return result;
	}

	@Override
	public AlarmInterface findAlarmBk(Integer bkId) {
		return realTimeAlarmMapper.findAlarmBk(bkId);
	}

	@Autowired
	private AlarmCountMapper alarmCountMapper;

	/**
	 * 统计时间段内各个布控分组的报警数量
	 * @return string
	 * @author zwc  2018/10/12
	 */
	@Override
	public List<AlarmCount> getAlarmNumByBuKongGroup(ClusterPass clusterPass) {
		List<AlarmCount> alarmNumByBuKongGroup = alarmCountMapper.getAlarmNumByBuKongGroup(clusterPass.getStartTime(), clusterPass.getEndTime());
		return alarmNumByBuKongGroup;
	}

	@Override
	public List<AlarmCount> getAlarmNumByBuKongGroup(String startTime, String endTime) {
		List<AlarmCount> alarmNumByBuKongGroup = alarmCountMapper.getAlarmNumByBuKongGroup(startTime, endTime);
		return alarmNumByBuKongGroup;
	}
	/**
	 * @Descriptions 统计时间段内各个布控分组的报警信息
	 * @return List
	 * @author xyc  2018/12/17
	 */
	@Override
	public List getAlarmInfoByBuKongGroup(String startTime, String endTime, List<AlarmCount> alarmCounts) {
		// 拿出对象中的groupid，组成新的数组传到后台
		List list2=new ArrayList();
		for(int i=0;i<alarmCounts.size();i++){
			String groupID=alarmCounts.get(i).getGroupID();
			list2.add(groupID);
		}
		// 获取结果
		List<RealTimeAlarm> realTimeAlarmList = alarmCountMapper.findAlarmByGroup(list2,startTime,endTime);
		// 处理时间多了.0问题
		for (int x = 0; x < realTimeAlarmList.size(); x++) {
			RealTimeAlarm r = realTimeAlarmList.get(x);
			if (r.getAlarmTime() != null) {
				r.setAlarmTime(r.getAlarmTime().substring(0, 19));
			}
		}
		Map<String,List<RealTimeAlarm>> map = new HashMap<>();
		List<Object> resultList = new ArrayList<>();
		// 将结果按照groupId分类
		for(RealTimeAlarm realTimeAlarm : realTimeAlarmList){
			if(map.containsKey(String.valueOf(realTimeAlarm.getBkGroupId()))){
				map.get(String.valueOf(realTimeAlarm.getBkGroupId())).add(realTimeAlarm);
			}else{
				List<RealTimeAlarm> tempList = new ArrayList<RealTimeAlarm>();
				tempList.add(realTimeAlarm);
				map.put(String.valueOf(realTimeAlarm.getBkGroupId()),tempList);
			}
		}
		// 将分类结果转换成map键值对
		for(String groupId : map.keySet()){
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("item",map.get(groupId));
			resultMap.put("groupid",groupId);
			resultList.add(resultMap);
		}
		return resultList;
	}

	/**
	 * 统计时间段内各个布控分组的报警信息
	 * @return List
	 * @author hyl  2018/10/12
	 */
	@Override
	public List getAlarmInfoByBuKongGroup(AlarmPersonDTO alarmPersonDTO) {
		// 拿出对象中的groupid，组成新的数组传到后台
		List<AlarmCount> list = alarmPersonDTO.getGroupIds();
		List list2=new ArrayList();
		for(int i=0;i<list.size();i++){
			String groupID=list.get(i).getGroupID();
			list2.add(groupID);
		}

		// 获取结果
		List<RealTimeAlarm> realTimeAlarmList = alarmCountMapper.findAlarmByGroup(list2,alarmPersonDTO.getBeginTime(),alarmPersonDTO.getEndTime());
		// 处理时间多了.0问题
		for (int x = 0; x < realTimeAlarmList.size(); x++) {
			RealTimeAlarm r = realTimeAlarmList.get(x);
			if (null != r.getAlarmTime()) {
				r.setAlarmTime(r.getAlarmTime().substring(0, 19));
			}
			if(null != r.getAlarmImagePath()){
			    r.setAlarmImagePath(imagePrefixProperties.getAlarmImageUrl() + r.getAlarmImagePath());
            }
		}
		Map<String,List<RealTimeAlarm>> map = new HashMap<>();
		List<Object> resultList = new ArrayList<>();
		// 将结果按照groupId分类
		for(RealTimeAlarm realTimeAlarm : realTimeAlarmList){
			if(map.containsKey(String.valueOf(realTimeAlarm.getBkGroupId()))){
				map.get(String.valueOf(realTimeAlarm.getBkGroupId())).add(realTimeAlarm);
			}else{
				List<RealTimeAlarm> tempList = new ArrayList<RealTimeAlarm>();
				tempList.add(realTimeAlarm);
				map.put(String.valueOf(realTimeAlarm.getBkGroupId()),tempList);
			}
		}
		// 将分类结果转换成map键值对
		for(String groupId : map.keySet()){
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("item",map.get(groupId));
			resultMap.put("groupid",groupId);
			resultList.add(resultMap);
		}
		return resultList;
	}

	@Override
	public Map<String,List<RealTimeAlarm>> getAlarmInfoByBuKongGroup(Date startTime,Date endTime,List<String> groupIds) {
		// 获取结果
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		String startTimeStr = sdf.format(startTime);
		String endTimeStr = sdf.format(endTime);
		List<RealTimeAlarm> realTimeAlarmList = alarmCountMapper.findAlarmByGroup(groupIds, startTimeStr, endTimeStr);
		// 处理时间多了.0问题
		for (int x = 0; x < realTimeAlarmList.size(); x++) {
			RealTimeAlarm r = realTimeAlarmList.get(x);
			if (r.getAlarmTime() != null) {
				r.setAlarmTime(r.getAlarmTime().substring(0, 19));
			}
		}
		Map<String,List<RealTimeAlarm>> map = new HashMap<>();
		// 将结果按照groupId分类
		for(RealTimeAlarm realTimeAlarm : realTimeAlarmList){
			if(map.containsKey(String.valueOf(realTimeAlarm.getBkGroupId()))){
				map.get(String.valueOf(realTimeAlarm.getBkGroupId())).add(realTimeAlarm);
			}else{
				List<RealTimeAlarm> tempList = new ArrayList<RealTimeAlarm>();
				tempList.add(realTimeAlarm);
				map.put(String.valueOf(realTimeAlarm.getBkGroupId()),tempList);
			}
		}
		return map;
	}

	@Override
	public Map getAlarmCount() {
		Map resultMap = new HashMap();
		int alarmCountTotal = alarmCountMapper.findAlarmTotalCount();
		int alarmCountToday = alarmCountMapper.findAlarmTodayCount();
		resultMap.put("alarmCountTotal",alarmCountTotal);
		resultMap.put("alarmCountToday",alarmCountToday);
		return resultMap;
	}

	@Override
	public int getTotalAlarmCount() {
		return alarmCountMapper.findAlarmTotalCount();
	}

	@Override
	public int getAlarmCount(Date startDate, Date endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if( null !=startDate && null != endDate){
           return realTimeAlarmMapper.getAlarmCountByTime(format.format(startDate), format.format(endDate));
        }
		return 0;
	}

	@Override
	public int getAlarmCount(String groupID, Date startDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if( null !=startDate && null != endDate){
            return realTimeAlarmMapper.getAlarmCountByTimeAndBkGroupId(format.format(startDate), format.format(endDate),groupID);
        }
	    return 0;
	}

	/**
	 * 根据报警id查询对应的报警信息  y 0105
	 * @param alarmId 报警id
	 * @return
	 */
	@Override
	public RealTimeAlarm getRealTimeAlarmByAlarmId(BigInteger alarmId) {
        RealTimeAlarm reamTimeAlarmByAlarmId = realTimeAlarmMapper.getReamTimeAlarmByAlarmId(alarmId);
        if(null != reamTimeAlarmByAlarmId){
            // 处理报警图片和布控图片路径问题
            reamTimeAlarmByAlarmId.setAlarmImagePath(imagePrefixProperties.getAlarmImageUrl()+reamTimeAlarmByAlarmId.getAlarmImagePath());
            reamTimeAlarmByAlarmId.setBkImagePath(imagePrefixProperties.getBukongImageUrl()+reamTimeAlarmByAlarmId.getBkImagePath());
            reamTimeAlarmByAlarmId.setAlarmTime(reamTimeAlarmByAlarmId.getAlarmTime().substring(0,19));
			reamTimeAlarmByAlarmId.setBackgroundImagePath(imagePrefixProperties.getAlarmImageUrl() + reamTimeAlarmByAlarmId.getBackgroundImagePath());
			return reamTimeAlarmByAlarmId;
        }
        return null;
	}
}
