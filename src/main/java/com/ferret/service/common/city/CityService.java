package com.ferret.service.common.city;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.service.common.city;
 * @auth: Administrator;
 * @since: 2017/12/15 0015;
 * @desc:
 */
public interface CityService {
    /**
     * 查询城市数据
     * @return
     */
    List<Map<String,Object>>   findAllObject();
    /**
     * 根据code查询某一指定的树节点下的树结构
     */
    List<Map<String,Object>> findByCode(@Param("code")Integer code);
    
    List<Map<String,Object>> findCityTest();

    /** 根据地区查出城市名称 */
    String findAreaName(Integer code);
}
