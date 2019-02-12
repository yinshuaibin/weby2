package com.ferret.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通过提特征服务JNI接口获取图片特征信息对应的实体类
 *
 * @author cc;
 * @version 1.0
 * @since 2018/4/14;
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFeature {
    /**
     * 代码
     */
    private Integer code;
    /**
     * 操作命令
     */
    private String cmd;
    /**
     * 状态,success为成功
     */
    private String error;
    /**
     * 提取的人像个数
     */
    private Integer facenum;
    /**
     * 特征信息
     */
    private ResInfo[] resInfo;

    @Data
    public static class ResInfo {
        // 特征数据
        private String featdata;

        private Integer confidence;
        private String eyeL;
        private String eyeR;
        private String mouth;
        private String nose;
        private Integer quality;
        private Integer pitch;
        private String rect;
        private Integer roll;
        private Integer yaw;

        public ImageFaceInfo convertToImageFaceInfo() {
            ImageFaceInfo info = new ImageFaceInfo();
            info.setConfidence(this.getConfidence());
            info.setYaw(this.getYaw());
            info.setRoll(this.getRoll());
            info.setPitch(this.getPitch());
            info.setEyeL(this.getEyeL());
            info.setEyeR(this.getEyeR());
            info.setMouth(this.getMouth());
            info.setNose(this.getNose());
            info.setQuality(this.getQuality());
            info.setRect(this.getRect());
            return info;
        }
    }
}
