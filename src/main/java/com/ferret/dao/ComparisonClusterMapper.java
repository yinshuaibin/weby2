package com.ferret.dao;


import com.ferret.bean.searchimage.SearchResult;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComparisonClusterMapper {
    /**
     * 查询tb_cluster_comparison表，获取记录集合（复用实体类）
     */
    @Select("SELECT id,person_id,uuid,create_time FROM tb_cluster_comparison WHERE create_time < date_add(now(), interval -1 minute)")
    @Results({
            @Result(property="sysId",column="id"),
            @Result(property="result",column="person_id"),
            @Result(property="taskId",column="uuid"),
            @Result(property="createTime",column="create_time")
    })
    List<SearchResult> getclusterComparison();

    @Delete("DELETE FROM tb_cluster_comparison WHERE id = #{id}")
    int deleteClusterComparison(@Param("id") String id);

    // @Update("UPDATE tb_cluster SET name = #{name},represent_img5 = #{imgUrl},idcard = #{idCard} WHERE person_id = #{personId}")
    @Update("UPDATE jh_person SET name = #{name},cardnumberpicpath = #{imgUrl},cardnumber = #{idCard} WHERE personid = #{personId}")
    int updateClusterByPersonId(@Param("personId") String result,@Param("idCard") String idCard,@Param("name") String name,@Param("imgUrl") String imgUrl);

}
