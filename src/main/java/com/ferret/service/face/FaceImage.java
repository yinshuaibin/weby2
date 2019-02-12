package com.ferret.service.face;

import lombok.Data;

@Data
public class FaceImage {
    private String faceData; // 人脸图片base64
    private int top = 0; // 人脸左上角坐标Y
    private int left = 0; // 人脸左上角坐标X
    private int width = 0; // 人脸区域宽度
    private int height = 0; // 人脸区域高度
}
