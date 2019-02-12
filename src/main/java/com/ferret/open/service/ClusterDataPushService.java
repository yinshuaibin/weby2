package com.ferret.open.service;

import com.ferret.open.bean.ClusterDataImage;
import com.ferret.open.bean.ClusterDataWei;

import java.util.List;

public interface ClusterDataPushService {
    List<ClusterDataImage> clusterDataPush(String id);

    String firstClusterDataPush();

    void updateClusterImgId(String id);

    String firstClusterWeiPush();

    List<ClusterDataWei> clusterWeiData(String createTime);

    void updateClusterWeiTime(String timeSing);
}
