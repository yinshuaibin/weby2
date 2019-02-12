package com.ferret.dao;

import com.ferret.bean.Cluster;

public interface ClusterMapper {
    int deleteByPrimaryKey(String personId);

    int insert(Cluster record);

    int insertSelective(Cluster record);

    Cluster selectByPrimaryKey(String personId);

    int updateByPrimaryKeySelective(Cluster record);

    int updateByPrimaryKey(Cluster record);
}