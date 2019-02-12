package com.ferret.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.ferret.bean.City;
import com.ferret.dao.provider.CityMapperProvider;

/**
 * @pack: com.ferret.system.bk.mapper;
 * @auth: Administrator;
 * @since: 2017/12/14 0014;
 * @desc:
 */
public interface CityMapper {

    /**
     * 查询数据
     */
    @Select("SELECT * FROM tb_city")
    @Results({
            @Result(property = "pCode",column = "p_code")
    })
    List<Map<String, Object>> findAllObject();

    /**
     * 根据城市节点查询树
     */
    @SelectProvider(type=CityMapperProvider.class,method="findByCode")
    @Results({
            @Result(property = "pCode",column = "p_code")
    })
    List<Map<String,Object>> findByCode(@Param("code") Integer code);
    
    /**
     * 根据roleId查询树
     */
    @Select("select* from tb_city tc where tc.code in (select camera_number from tb_role_camera tca where tca.role_id=#{roleId})")
    @Results({
        @Result(property = "pCode",column = "p_code")
    })
    List<Map<String,Object>> findByRoleId(@Param("roleId") String roleId);

    @Select("SELECT * FROM tb_city WHERE code = '4101'or code ='411324' or code ='411424'")
	List<Map<String, Object>> findCityTest();
    
    /**
     * 根据地区id查询城市
     */
    @Select("select id,code,p_code,name,enabled,center_point_longitude,center_point_latitude,southWest_longitude,southWest_latitude,northEast_longitude,northEast_latitude from tb_city where code=#{code}")
    
    City findCityByArea(@Param("code") String code);

    /**
     * @Description 根据城市地区code 查出省市
     * @param code
     * @date 2019-01-14 09:18:56
     * @author xieyingchao
     */
    @Select("SELECT `name`, code FROM tb_city WHERE `code` = #{code}")
    City findCityNameByCode(@Param("code") Integer code);
}
