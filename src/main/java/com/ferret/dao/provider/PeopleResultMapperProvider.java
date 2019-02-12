package com.ferret.dao.provider;

import com.ferret.bean.searchimage.SearchImage;
import com.ferret.bean.searchimage.UploadImgList;

public class PeopleResultMapperProvider {

	public String saveImageInfo(SearchImage simg) {
		//VALUES('11','222','22'),('11','222','22')
		String taskId = simg.getTaskId();
		StringBuffer sbBuffer = new StringBuffer("INSERT INTO tb_receiveimage(imagePath_local,imageUrl,taskId)VALUES");
		for (UploadImgList uploadImgList : simg.getUploadImgList()) {
			sbBuffer.append("('").append(uploadImgList.getName()).append("','").append(uploadImgList.getUrl()).append("','").append(taskId).append("'),");
		}
		String string = sbBuffer.substring(0, sbBuffer.length()-1).toString();
		return string;
	}
}
