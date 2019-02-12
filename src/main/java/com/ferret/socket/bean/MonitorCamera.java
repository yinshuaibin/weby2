package com.ferret.socket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cc;
 * @since 2017/12/19;
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorCamera {

    /**
     * 摄像头id
     */
    private Integer cameraId;

    /**
     * 用户id
     */
    private Integer userId;
}
