package com.ferret.dao;

import com.ferret.bean.BuKongSample;

public interface BuKongSampleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuKongSample record);

    int insertSelective(BuKongSample record);

    BuKongSample selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuKongSample record);

    int updateByPrimaryKey(BuKongSample record);
}