package com.ferret.dao;

import com.ferret.bean.Background;

public interface BackgroundMapper {
    int deleteByPrimaryKey(Integer historypassId);

    int insert(Background record);

    int insertSelective(Background record);

    Background selectByPrimaryKey(Integer historypassId);

    int updateByPrimaryKeySelective(Background record);

    int updateByPrimaryKey(Background record);
}