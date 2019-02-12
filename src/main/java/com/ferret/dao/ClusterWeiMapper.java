package com.ferret.dao;

import com.ferret.bean.ClusterWei;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ClusterWeiMapper {

    int deleteByPrimaryKey(String personId);

    int insert(ClusterWei record);

    int insertSelective(ClusterWei record);

    ClusterWei selectByPrimaryKey(String personId);

    int updateByPrimaryKeySelective(ClusterWei record);

    int updateByPrimaryKey(ClusterWei record);

    ArrayList<ClusterWei> listAll();

    List<ClusterWei> listRecentRecords();
}