package com.ferret.service.cluster;

import com.ferret.bean.ClusterPass;
import com.ferret.bean.ClusterWei;
import com.ferret.dto.PageDTO;

import java.util.List;
import java.util.Map;

public interface ClusterWeiZuService {

    /**
     * 通过时间段,查询维族档案数据
     * @param clusterPass
     * @return
     */
    List<ClusterPass> findClusterWeiByDate(ClusterPass clusterPass);
    /**
     * 通过时间段,查询维族档案总条数
     * @param clusterPass
     * @return
     */
    Integer findTotalByDate(ClusterPass clusterPass);

    /**
     * 获取最新的tb_cluster_wei中的6条维族人数据
     * y 1012
     * @return
     */
    public Map getNewClusterWei();

    /**
     * 通过person_id,查询tb_cluster_image中符合记录的所有数据 并分页
     * @param clusterPass
     * @return
     */
    List<ClusterPass> findClusterByPersonId(ClusterPass clusterPass);


    ClusterPass getPhotoByPersonId(String personId);

    /**
     * 分页读取维族聚类数据
     * @param pageSize 页大小
     * @param pageNum 页码
     * @return 分页对象
     */
    PageDTO<ClusterWei> listByPage(Integer pageNum , Integer pageSize);


    List<ClusterWei> listRecentRecords();
}
