package com.ferret.service.face;

import com.ferret.bean.ImageFace;

import java.util.List;

/**
 * 人脸服务
 */
public interface FaceService {
    Float faceCompareInterface(String imageBase64_1, String imageBase64_2);


    /**
     * 人脸截取    y 0103  新中间件
     * @param imageBase64
     * @return
     */
    ImageFace FaceExtraction(String imageBase64);
}
