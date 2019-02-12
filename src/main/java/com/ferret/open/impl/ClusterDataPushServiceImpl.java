package com.ferret.open.impl;

import com.ferret.open.bean.ClusterDataImage;
import com.ferret.open.bean.ClusterDataWei;
import com.ferret.open.dao.ClusterDataPushDao;
import com.ferret.open.service.ClusterDataPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterDataPushServiceImpl implements ClusterDataPushService {

    @Autowired
    private ClusterDataPushDao clusterDataPushDao;
    /**
     * 聚类档案推送（获取数据库保存的上一次推送位置的id,继续推送之后的数据）
     */
    @Override
    public List<ClusterDataImage> clusterDataPush(String id) {
        return clusterDataPushDao.clusterDataPush(id);
    }
    /**
     * 获取上一次聚类推送点（id）
     */
    @Override
    public String firstClusterDataPush() {
        return clusterDataPushDao.firstClusterDataPush();
    }
    /**
     * 更新聚类推送点
     */
    @Override
    public void updateClusterImgId(String id) {
        clusterDataPushDao.updateClusterImgId(id);
    }

    /**
     * 获取上一次维族人数据推送点（时间）
     */
    @Override
    public String firstClusterWeiPush() {
        return clusterDataPushDao.firstClusterWeiPush();
    }
    /**
     * 维族人数据 推送（获取数据库保存的上一次推送位置的createTime,继续推送之后的数据）
     */
    @Override
    public List<ClusterDataWei> clusterWeiData(String createTime) {
        return clusterDataPushDao.clusterWeiData(createTime);
    }

    /**
     * 更新维族人数据 推送时间点
     */
    @Override
    public void updateClusterWeiTime(String timeSing) {
        clusterDataPushDao.updateClusterWeiTime(timeSing);
    }

}
