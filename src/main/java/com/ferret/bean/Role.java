package com.ferret.bean;

import java.util.List;

/**
 * 修改人:y
 * @author y
 * 修改原因:因需求改动,增加一个字段 authorityIds
 * 修改人日期:0724
 *
 */
public class Role {
    public List<String> getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(List<String> authorityIds) {
		this.authorityIds = authorityIds;
	}

	private String roleId;

    private String roleName;

    private String roleDesc;

    private String enabled;

	//角色所带权限
    private List<Authc> authcs;
   
    //布控摄像头
    private List<String> cameraId;

    //页面->地区编号
    private List<Integer> areaId;
    
    //更改权限的时候接受页面传递的数据 
    // 格式:authority_id-parent_id
    
    private List<String> authorityIds;
    
	//页面树的树结构
    private String title;
    private boolean expand;
    private List<Authc> children;
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<Authc> getAuthcs() {
		return authcs;
	}

	public void setAuthcs(List<Authc> authcs) {
		this.authcs = authcs;
	}

	public List<String> getCameraId() {
		return cameraId;
	}

	public void setCameraId(List<String> cameraId) {
		this.cameraId = cameraId;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public List<Authc> getChildren() {
		return children;
	}

	public void setChildren(List<Authc> children) {
		this.children = children;
	}

    public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
    public List<Integer> getAreaId() {
		return areaId;
	}

	public void setAreaId(List<Integer> areaId) {
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", enabled=" + enabled
				+ ", authcs=" + authcs + ", cameraId=" + cameraId + "]";
	}
	
	public void copy2Fileds() {
		this.title=this.roleDesc;
		this.expand=false;
		this.children=this.authcs;
	}
    
}