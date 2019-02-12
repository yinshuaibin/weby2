package com.ferret.bean;

import java.io.Serializable;

/**
 * @pack: com.ferret.system.bk.entity;
 * @auth: Administrator;
 * @since: 2017/12/14 0014;
 * @desc:
 */
public class City implements Serializable {
	/** ID */
	private Integer id;
	/** 城市ID */
	private Integer code;
	/** 城市父ID */
	private Integer pCode;
	/** 城市名称 */
	private String name;
	/** 是否可用 */
	private Integer enabled;
	/**
	 * @pack: com.ferret.system.bk.entity;
	 * @auth: huyunlong;
	 * @since: 2018/07/10 18:08;
	 * @desc:增加城市的中心点及边界经纬度
	 */
	/** 中心点经度 */
	private Double centerPointLongitude;
	/** 中心点纬度 */
	private Double centerPointLatitude;
	/** 左下经度 */
	private Double southWestLongitude;
	/** 左下纬度 */
	private Double southWestLatitude;
	/** 右上经度 */
	private Double northEastLongitude;
	/** 右上纬度 */
	private Double northEastLatitude;

	public City() {

	}

	public City(Integer id, Integer code, Integer pCode, String name, Integer enabled) {
		this.id = id;
		this.code = code;
		this.pCode = pCode;
		this.name = name;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getpCode() {
		return pCode;
	}

	public void setpCode(Integer pCode) {
		this.pCode = pCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Double getCenterPointLongitude() {
		return centerPointLongitude;
	}

	public void setCenterPointLongitude(Double centerPointLongitude) {
		this.centerPointLongitude = centerPointLongitude;
	}

	public Double getCenterPointLatitude() {
		return centerPointLatitude;
	}

	public void setCenterPointLatitude(Double centerPointLatitude) {
		this.centerPointLatitude = centerPointLatitude;
	}

	public Double getSouthWestLongitude() {
		return southWestLongitude;
	}

	public void setSouthWestLongitude(Double southWestLongitude) {
		this.southWestLongitude = southWestLongitude;
	}

	public Double getSouthWestLatitude() {
		return southWestLatitude;
	}

	public void setSouthWestLatitude(Double southWestLatitude) {
		this.southWestLatitude = southWestLatitude;
	}

	public Double getNorthEastLongitude() {
		return northEastLongitude;
	}

	public void setNorthEastLongitude(Double northEastLongitude) {
		this.northEastLongitude = northEastLongitude;
	}

	public Double getNorthEastLatitude() {
		return northEastLatitude;
	}

	public void setNorthEastLatitude(Double northEastLatitude) {
		this.northEastLatitude = northEastLatitude;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", code=" + code + ", pCode=" + pCode + ", name=" + name + ", enabled=" + enabled
				+ ", centerPointLongitude=" + centerPointLongitude + ", centerPointLatitude=" + centerPointLatitude
				+ ", southWestLongitude=" + southWestLongitude + ", southWestLatitude=" + southWestLatitude
				+ ", northEastLongitude=" + northEastLongitude + ", northEastLatitude=" + northEastLatitude + "]";
	}
	
}
