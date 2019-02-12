package com.ferret.service.dynamic;

import com.ferret.bean.InterfaceBean.Dynamic;

import java.util.Date;
import java.util.List;

public interface ImageDynamicService {

    /** 特征提取人脸比对服务重新加载人脸库请求 */
    boolean reloadFaceSearch();
    /** 动态反查重新加载 */
    boolean reloadFaceTrauls(String loadType);

    /** 动态检索请求 */
    List<Dynamic> DynamicFaceTrauls(String startTime, String endTime, float threshold, String imageData, List<String> cameraIds, String projectName, int count);
}
