package com.ferret.bean;

import lombok.Data;

@Data
public class RoleAuthc {
	private Integer id;
	private String roleId;
	private String authorityId;

	public RoleAuthc() {
	}

	public RoleAuthc(String roleId, String authorityId) {
		this.roleId = roleId;
		this.authorityId = authorityId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}
	
}
