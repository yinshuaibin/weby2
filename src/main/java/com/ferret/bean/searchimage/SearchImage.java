package com.ferret.bean.searchimage;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SearchImage {
	private String taskId;
	private String imageBase64;
	private Float threshold;
	private MultipartFile[] upload;
	private int resultSize=15;
	private List<UploadImgList> uploadImgList;
}
