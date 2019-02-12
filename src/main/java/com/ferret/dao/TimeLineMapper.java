package com.ferret.dao;

import com.ferret.bean.TimeLine;

public interface TimeLineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeLine record);

    int insertSelective(TimeLine record);

    TimeLine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeLine record);

    int updateByPrimaryKey(TimeLine record);
}