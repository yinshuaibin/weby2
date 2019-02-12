package com.ferret.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.ferret.bean.Config;

/**
 * 对tb_config表进行操作
 * @author Administrator
 *
 */
@Repository
public interface ConfigMapper {
	
    /**
     * 插入: 新增一条记录
     * @param config
     * @return
     */
	@Insert("INSERT INTO tb_config "
		  + "(sys_name, name, value, description) "
		  + "VALUES "
		  + "(#{sysName}, #{name}, #{value}, #{description}) ")
	int insert(Config config);
	
	/**
	 * 查询: 查询出所有记录
	 * @return
	 */
	@Select("SELECT sys_name, name, value, description FROM tb_config")
	@Results({
		@Result(id=true, property="id", column="id"),
		@Result(property="sysName", column="sys_name"),
		@Result(property="name", column="name"),
		@Result(property="value", column="value"),
		@Result(property="description", column="description")
	})
	List<Config> selectAll();
	
	/**
	 * 修改: 根据ID修改记录
	 * @param config
	 * @return
	 */
	@Update("UPDATE tb_config "
		  + "SET sys_name = #{sysName}, "
		  + "    name = #{name}, "
		  + "    value = #{value}, "
		  + "    description = #{description} "
		  + "WHERE id = #{id}")
	int updateById(Config config);
	
	/**
	 * 删除: 根据id删除记录
	 * @param config
	 * @return
	 */
	@Delete("DELETE FROM tb_config WHERE id = #{id}")
	int deleteById(Config config);
}