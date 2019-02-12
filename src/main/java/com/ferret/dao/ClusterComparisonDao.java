package com.ferret.dao;

import com.ferret.bean.Cluster;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterComparisonDao {

    int insertTaskPersons(List<Cluster> list);

    @Insert("insert into tb_cluster_comparison (person_id,uuid) values(#{personId},#{txId})")
    int insertTaskPerson(Cluster cluster);

    @Select("SELECT count(person_id) FROM tb_cluster_comparison WHERE person_id = #{personId}")
    int existTask(@Param("personId") String personId);
}
