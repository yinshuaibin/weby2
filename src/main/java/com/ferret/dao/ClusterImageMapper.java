package com.ferret.dao;

import com.ferret.bean.ClusterImage;

public interface ClusterImageMapper {
    int insert(ClusterImage record);

    int insertSelective(ClusterImage record);
}