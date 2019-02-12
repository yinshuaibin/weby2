package com.ferret.service.cluster.impl;

import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.CameraInfo;
import com.ferret.bean.Cluster;
import com.ferret.bean.RealTimeImage;
import com.ferret.bean.Stranger;
import com.ferret.bean.searchimage.SearchImage;
import com.ferret.common.base.Common;
import com.ferret.dao.ClusterImgMapper;
import com.ferret.dto.ClusterAdvanceDTO;
import com.ferret.dto.ClusterBrowseDTO;
import com.ferret.service.cluster.ClusterImgService;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Service
public class ClusterImgServiceImpl implements ClusterImgService, Common {

	@Autowired
	private ClusterImgMapper clusterImgMapper;

	/**
	 * @Descriptions 根据personId 查询出所有同行人
	 * @Author xyc 2018/12/22
	 * @throws Exception
	 */
	@Override
	public List<Cluster> tongxingPerson(String startTime, String endTime, Integer minNum, String personId) throws Exception {
		List<Cluster> clusters = clusterImgMapper.findTXCluster(personId, startTime,
				endTime, minNum);
		if(clusters.size()>0) {
			return clusters;
		}else {
			return null;
		}
	}

	/**
	 * @Descriptions 陌生人查询（分页查询）
	 * @param startTime 开始时间
	 * @param stime     回溯时间
	 * @param endTime   结束时间
	 * @param pageNum      页数
	 * @param pageSize     每页个数
	 * @param cameraInfos  相机集合
	 * @return
	 */
	@Override
	public List<Stranger> getStrangerByTimeAndCamera(String startTime, String stime, String endTime, int pageNum, int pageSize, List<CameraInfo> cameraInfos) {
		// 开始条数
		int startNum = (pageNum - 1) * pageSize;
		// 调用存储过程,分页查询,每次21条
		List<Stranger> result = clusterImgMapper.getStrangerByCameraAndTime(startTime,stime,endTime,startNum,pageSize,cameraInfos);
		//处理数据库时间多（.0）问题
		for (Stranger s:result) {
			if (s.getCreateTime() != null) {
				s.setCreateTime(s.getCreateTime().substring(0, 19));
			}
		}
		return result;
	}

	/**
	 * @Descriptions 陌生人查询（查询出总数量）
	 * @Author xieyingchao 2018/12/22
	 * @return
	 */
	@Override
	public int getStrangerCountByTimeAndCamera(String startTime, String stime, String endTime, List<CameraInfo> cameraInfos) {
		return clusterImgMapper.getStrangerByCameraAndTime_count(startTime,stime,endTime,cameraInfos);
	}

	/**
	 * @Descrptions 陌生人查询（根剧personId查询陌生人得详细抓拍记录）
	 * @param startTime
	 * @param stime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @param cameraInfos
	 * @param personId
	 * @return
	 */
	@Override
	public List<Stranger> getStrangerByPersonId(String startTime, String stime, String endTime, int pageNum, int pageSize, List<CameraInfo> cameraInfos,String personId) {
		// 开始条数
		int startNum = (pageNum - 1) * pageSize;

		// 根据时间段,相机,personId,获取对应的list集合
		List<Stranger> result = clusterImgMapper.getStrangerByPersonId(startTime,stime,endTime,startNum,pageSize,cameraInfos,personId);
		//处理时间多了.0问题
		for (Stranger s:result) {
			if (s.getCreateTime() != null) {
				s.setCreateTime(s.getCreateTime().substring(0, 19));
			}
		}
		return result;
	}

	/**
	 * @Description 通过jh_facepic中最大的id,以及相机ip,查询实时抓拍图像
	 * @param cameraIp
	 * @param maxID 
	 * @date 2019-01-02 13:41:48
	 * @author xieyingchao
	 */
	@Override
	public List<RealTimeImage> getMonitorImageByCameraIp(String cameraIp,Integer maxID) {
		// 否则是正常的查询
		List<RealTimeImage> resultList = clusterImgMapper.findImageByIpAndMaxID(cameraIp,maxID);
		return resultList;
	}

    /**
     * @Description 查询最大的ImageId
     * @date 2019-01-02 13:29:50
     * @author xieyingchao
     */
	@Override
	public int getMaxImageIDByCameraIp() {
		return clusterImgMapper.getMaxID();
	}

	/**
	 * @Descriptions 获取最新的聚类新进人员
	 * @author yinshuaibin 2018/12/22
	 * @param cameraInfos 所选择的的相机列表
	 * @param pageSize 所要获取的条数
	 * @return
	 */
	@Override
	public List<Stranger> getNewCluster(List<CameraInfo> cameraInfos, Integer pageSize) {
		//默认查询21条
		if(pageSize==null){
			pageSize=24;
		}
		List<Stranger> result = clusterImgMapper.getNewCluster(cameraInfos,pageSize);
		for(int x=0;x<result.size();x++) {
			Stranger strangerResult = result.get(x);
			if(strangerResult.getCreateTime().length()>19) {
				strangerResult.setCreateTime(strangerResult.getCreateTime().substring(0, 19));
			}
		}
		return result;
	}

	/**
	 * @Descriptions 新的实时陌生人逻辑,页面发送请求到后台时,根据数据库时间查询
	 * @Author 2018/12/22 yinshuaibin
	 * @param cameraInfos 所选择的的相机列表
	 * @return
	 */
	@Override
	public List<Stranger> getStranger(List<CameraInfo> cameraInfos) {
		List<Stranger> strangers = clusterImgMapper.getStranger(cameraInfos);

		return strangers;
	}

	/**
	 * @Descriptions 档案高级检索 根据地区码,开始时间,结束时间查询出现的所有人,以及每个人出现的次数
	 * @return
	 */
	@Override
	public List<ClusterAdvanceDTO> getPersonByTimeAndArea(String startTime, String endTime, List<String> areaCodes) {

		//根据相机ip,起始时间段查询出符合要求的数据
		List<ClusterAdvanceDTO> getPersonByTimeAndArea = clusterImgMapper.getPersonByTimeAndArea(startTime,endTime,areaCodes);
		return  getPersonByTimeAndArea;
	}


	/**
	 * @Descriptions 根据personid来查询人员的轨迹
	 * @return
	 */
	@Override
	public List<ClusterBrowseDTO> getPersonTrajectory(String startTime, String endTime, String personId, List<String> areaCodes) {

		return clusterImgMapper.getPersonTrajectory(personId,startTime,endTime,areaCodes);
	}

	/**
	 * @Descriptions 获取jh_cluserpic表的最大id
	 * @return
	 */
	@Override
	public BigInteger getMaxId() {
		return clusterImgMapper.getMaxId();
	}

	/**
	 * @Descriptions : 第一次获取cluster_pic表的最大id，一分钟后再获取该表的最大id，获取两个id之间的数据 并大于最小id的时间筛选数据
	 * @return
	 */
	@Override
	public List<Stranger> realTimeStranger(BigInteger firstId,BigInteger lastId,List<CameraInfo> cameraList) {
		return clusterImgMapper.realTimeStranger(firstId,lastId,cameraList);
	}
}
