package com.ferret.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cluster {
    /**
     * 聚类维族人id,和文件夹名一致
     */
    private String personId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 当前id下的图片总数
     */
    private Integer count;
    /**
     * 当前id查询时间段的出现时间
     */
    private String startTime;
    /**
     * 当前id查询时间段的离开时间
     */
    private String endTime;
    /**
     * 同行人出现时间
     */
    private String txTime;
    /**
     * 相机ip
     */
    private String cameraip;
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String represent_img;//用来接收目标图

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String represent_img1;//用来接收同行人图

    private String txId;
   /**
     * 设备名称
     */
    private String name;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 在完善信息接口的时候使用   y  0114
     */
    private String imagePath;

    private String represent_img5;
    private String idCard;
    public String getRepresent_img1() {
		return represent_img1;
	}

	public void setRepresent_img1(String represent_img1) {
		this.represent_img1 = represent_img1;
	}

	public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
}