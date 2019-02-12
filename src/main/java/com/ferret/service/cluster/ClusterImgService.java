package com.ferret.service.cluster;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.Cluster;
import com.ferret.bean.RealTimeImage;
import com.ferret.bean.Stranger;
import com.ferret.dto.ClusterAdvanceDTO;
import com.ferret.dto.ClusterBrowseDTO;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ClusterImgService {
	// 同行查询
	List<Cluster> tongxingPerson(String startTime, String endTime, Integer minNum, String personId) throws Exception;
	// 陌生人查询（查询结果  分页）
	List<Stranger> getStrangerByTimeAndCamera(String startTime, String stime, String endTime, int startNum, int pageSize, List<CameraInfo> cameraInfos);
	// 陌生人查询 （查询结果长度）
	int getStrangerCountByTimeAndCamera(String startTime, String stime, String endTime, List<CameraInfo> cameraInfos);
	// 陌生人查询 （根据personId 查询详细信息）
	List<Stranger> getStrangerByPersonId(String startTime, String stime, String endTime, int startNum, int pageSize, List<CameraInfo> cameraInfos,String personId);
	// 获取实时抓拍
	List<RealTimeImage> getMonitorImageByCameraIp(String cameraIp,Integer maxID);
	// 获取实施抓拍中最大得ImageID
	int getMaxImageIDByCameraIp();

	List<Stranger> getNewCluster(List<CameraInfo> cameraInfos, Integer pageSize);

	List<Stranger> getStranger(List<CameraInfo> cameraInfos);

	/**
	 * 根据地区码,开始时间,结束时间查询出现的所有人,以及每个人出现的次数
	 * @return
	 */
	List<ClusterAdvanceDTO> getPersonByTimeAndArea(String startTime, String endTime, List<String> areaCodes);


	/**
	 * @Descriptions 根据personid来查询人员的轨迹
	 * @return
	 */
	List<ClusterBrowseDTO> getPersonTrajectory(String startTime, String endTime, String personId, List<String> areaCodes);

	/**
	 * @Descriptions 获取jh_cluserpic表的最大id
	 * @return
	 */
	BigInteger getMaxId();

	/**
	 * 陌生人查询 （第一次获取cluster_pic表的最大id，一分钟后再获取该表的最大id，获取两个id之间的数据 并大于最小id的时间筛选数据）
	 * @param cameraList
	 * @return
	 */
	List<Stranger> realTimeStranger(BigInteger firstId,BigInteger lastId,List<CameraInfo> cameraList);
}
