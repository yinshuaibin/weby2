package com.ferret.dao.provider;

import org.apache.ibatis.annotations.Param;

import com.ferret.bean.Role;

public class RoleMapperProvider {

	public String updateSql(@Param("role") Role role) {

		StringBuilder sql = new StringBuilder("UPDATE tb_role SET ");

		// 动态拼接sql, 在属性非空时进行拼接
		if (role.getRoleName() != null && !"".equals(role.getRoleName().trim())) {
			sql.append(" role_name = #{role.roleName}, ");
		}
		if (role.getRoleDesc() != null && !"".equals(role.getRoleDesc().trim())) {
			sql.append(" role_desc = #{role.roleDesc}, ");
		}

		// 删除最后的逗号
		sql.deleteCharAt(sql.lastIndexOf(","));
		// 增加过滤条件
		sql.append(" WHERE role_id = #{role.roleId} ");

		return sql.toString();
	}
}
