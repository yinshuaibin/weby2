package com.ferret.bean;

public class BuKongCamera {
    private Integer id;

    private String bkGroupId;

    private String cameraId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getBkGroupId() {
		return bkGroupId;
	}

	public void setBkGroupId(String bkGroupId) {
		this.bkGroupId = bkGroupId;
	}

	public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

	@Override
	public String toString() {
		return "BuKongCamera [id=" + id + ", bkGroupId=" + bkGroupId + ", cameraId=" + cameraId + "]";
	}
    
    
}