package com.ferret.service.cluster;

import com.ferret.bean.ClusterPass;
import com.ferret.bean.ClusterSearch;
import com.ferret.bean.ImageFace;
import com.ferret.dto.ClusterBrowseDTO;

import java.util.List;
import java.util.Map;

/**
 * 修改人:y
 *
 * 修改日期:0716
 * @author Administrator
 *
 */
public interface ClusterService {


	/**
	 * 通过时间段,查询档案数据
	 *  修改人:y
	 * 修改时间: 0716
	 * @param clusterPass
	 * @return
	 */
	@Deprecated
	List<ClusterBrowseDTO> findClusterByDate(ClusterPass clusterPass);
	// 通过时间段,查询档案数据
	@Deprecated
	List<ClusterBrowseDTO> findClusterByDate(String startTime, String endTime, int minNum, int startNum);
	List<ClusterBrowseDTO> findClusterByDate(String startTime, String endTime, int minNum, int pageNum, int pageSize);
	/**
	 * 通过时间段,查询档案总条数
	 * 修改人:y
	 * 修改时间: 0716
	 * @param clusterPass
	 * @return
	 */
	@Deprecated
	Integer findTotalByDate(ClusterPass clusterPass);
	// 通过时间段查询档案总条数
	Integer findTotalByDate(String startTime, String endTime, int minNum);

	/**
	 * 通过person_id,查询tb_cluster_pass中符合记录的所有数据 并分页
	 * 修改人:y
	 * 修改时间: 0716
	 * @param clusterPass
	 * @return
	 */
	@Deprecated
	List<ClusterBrowseDTO> findClusterByPersonId(ClusterPass clusterPass);

	List<ClusterBrowseDTO> findClusterByPersonId(int pageNum, int pageSize, String personId);
	/**
	 * @Descriptions 通过person_id,查询tb_cluster_pass中最近一周记录的所有数据 用于地图标记最近一周的轨迹
	 * @param personId
	 * @Author xyc
	 */
	List<ClusterPass>  findClusterByPersonId(String personId);
	/**
	 * @Description 通过person_id去tb_cluster_pass去取符合条件的第一条数据
	 * @param count  返回张数
	 * @param threshold 阈值
	 * @param imageData 图片（Base64）
	 * @author xyc 2018/12/17
	 */
	List<ClusterBrowseDTO> findClusterByPerId(int count, float threshold, String imageData);

	/** 根据personId 聚类信息代表图 */
	ClusterBrowseDTO getPhotoByPersonId(String personId);

	/** 根据personId 查询落地后的身份信息（姓名，身份证号，身份证照片） */
	ClusterBrowseDTO getPersonInfoByPersonId(String personId);

}
