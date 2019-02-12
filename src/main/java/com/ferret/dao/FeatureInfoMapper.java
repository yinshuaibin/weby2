package com.ferret.dao;

import com.ferret.bean.FeatureInfo;

public interface FeatureInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeatureInfo record);

    int insertSelective(FeatureInfo record);

    FeatureInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeatureInfo record);

    int updateByPrimaryKey(FeatureInfo record);
}