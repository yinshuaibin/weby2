package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubImageInfoObject {
	@JsonProperty("Data")
	private String data; //"6L+Z5piv5LiA5Liq5paH5Lu255qEQmFzZTY057yW56CB",
	@JsonProperty("DeviceID")
	private String deviceID; //"64010000001320000001"
	@JsonProperty("Width")
	private int width; //50
	@JsonProperty("ImageID")
	private String imageID; //"1"
	@JsonProperty("Height")
	private int height; //20
	@JsonProperty("FileFormat")
	private String fileFormat; //"Jpeg"
	@JsonProperty("EventSort")
	private int eventSort; //3
	@JsonProperty("StoragePath")
	private String storagePath; //"http://192.168.1.1:8080/VIID/Images/1"
	@JsonProperty("ShotTime")
	private String shotTime; //"20171019133202"
}
