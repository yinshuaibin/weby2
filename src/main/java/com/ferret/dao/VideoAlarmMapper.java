package com.ferret.dao;

import com.ferret.bean.VideoAlarm;

public interface VideoAlarmMapper {
    int deleteByPrimaryKey(Integer videoId);

    int insert(VideoAlarm record);

    int insertSelective(VideoAlarm record);

    VideoAlarm selectByPrimaryKey(Integer videoId);

    int updateByPrimaryKeySelective(VideoAlarm record);

    int updateByPrimaryKey(VideoAlarm record);
}