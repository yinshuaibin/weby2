package com.ferret.bean;

import java.util.List;

public class BuKongGroup {
    private String bkGroupId;
    
    private String userId;

    private String bkGroupName;

    private String description;
    
    private String deletereason;
    
    private boolean enabled;
    
    private String bkRoleId;
    
    private List<String> cameraId;

    public String getBkGroupId() {
		return bkGroupId;
	}

	public void setBkGroupId(String bkGroupId) {
		this.bkGroupId = bkGroupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBkGroupName() {
        return bkGroupName;
    }

    public void setBkGroupName(String bkGroupName) {
        this.bkGroupName = bkGroupName == null ? null : bkGroupName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public List<String> getCameraId() {
		return cameraId;
	}

	public void setCameraId(List<String> cameraId) {
		this.cameraId = cameraId;
	}

	public String getDeletereason() {
		return deletereason;
	}

	public void setDeletereason(String deletereason) {
		this.deletereason = deletereason;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getBkRoleId() {
		return bkRoleId;
	}

	public void setBkRoleId(String bkRoleId) {
		this.bkRoleId = bkRoleId;
	}

	@Override
	public String toString() {
		return "BuKongGroup [bkGroupId=" + bkGroupId + ", userId=" + userId + ", bkGroupName=" + bkGroupName
				+ ", description=" + description + ", deletereason=" + deletereason + ", enabled=" + enabled
				+ ", bkRoleId=" + bkRoleId + ", cameraId=" + cameraId + "]";
	}


    
	

    
    
    
    
    
}