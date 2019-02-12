package com.ferret.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DicParser {

    @Select("SELECT name FROM tb_common_dic WHERE type = #{type} AND code = #{code}")
    String getName(@Param("code")String code, @Param("type")String type);
}
