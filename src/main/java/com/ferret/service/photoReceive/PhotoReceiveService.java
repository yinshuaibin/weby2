package com.ferret.service.photoReceive;

public interface PhotoReceiveService {

    String findUrlByToken(String token);

    /**
     * 将接口传递过来的图片聚类
     * @param token 请求接口标记
     * @param base64 图片base64
     * @param sendImagePath 传递过来的图片名称 摄像头ip_通道号_时间戳_face.jpg 示例：192.168.1.1_01_2018110200000001_face.jpg
     * @return
     */
    String makeTask(String token,String base64,String sendImagePath);
}
