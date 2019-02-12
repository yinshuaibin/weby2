package com.ferret.dao;

import com.ferret.bean.Authc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthcMapper {

	/**
	 * 查询: 根据角色id查询对应的启用的权限
	 *
	 * @param roleId
	 * @return
	 */
	@Select("SELECT authority_id, authority_name, authority_url, authority_desc,menu_name,parent_id,icon_type,module_url FROM tb_authc "
			+ "WHERE authority_id IN (SELECT authority_id FROM tb_role_authc WHERE role_id = #{roleId}) AND enabled = 1 order by  authority_id")
	@Results({ @Result(id = true, property = "authorityId", column = "authority_id"),
			@Result(property = "authorityName", column = "authority_name"),
			@Result(property = "authorityUrl", column = "authority_url"),
			@Result(property = "authorityDesc", column = "authority_desc"),
			@Result(property = "menuName", column = "menu_name"), @Result(property = "parentId", column = "parent_id"),
			@Result(property = "iconType", column = "icon_type"),
			@Result(property = "moduleUrl", column = "module_url") })
	List<Authc> selectByRoleId(String roleId);

	/**
	 * 查询: 查询所有启用的权限
	 *
	 * @return
	 */
	@Select("SELECT authority_id, authority_name, authority_url, parent_id,authority_desc FROM tb_authc WHERE enabled = 1")
	@Results({ @Result(id = true, property = "authorityId", column = "authority_id"),
			@Result(property = "authorityName", column = "authority_name"),
			@Result(property = "authorityUrl", column = "authority_url"),
			@Result(property = "authorityDesc", column = "authority_desc"),
			@Result(property = "parentId", column = "parent_id"),
			@Result(property = "uiComponentName", column = "ui_Component_name") })
	List<Authc> selectAll();

	/**
	 * 根据roleId来查询对应的权限的跳转链接名字
	 */
	@Select("SELECT menu_name from tb_authc where authority_id in(select tr.authority_id from tb_role_authc tr where role_id=#{roleId})  ")
	List<String> selectMenuNameByRoleId(String roleId);
}
