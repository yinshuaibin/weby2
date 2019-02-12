package com.ferret.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import java.util.Date;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraInfo {
    /**
     * 注释（自增ID）
     */
    private int id;
    /**
     * 相机Id 唯一，例：ip_通道  192.168.0.66_01
     */
    private String  cameraId;
    /**
     * 父节点
     */
    private String groupId;

    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备IP
     */
    private String ip;
    /**
     * rtsp全路径, 包括IP端
     */
    private String rtspUrl;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 设备方向[度数，以正北方向为零度，顺时针取值]
     */
    private int direction;
    /**
     * 生产商,用数字字符表示,海康:1;大华:2;
     */
    private String manufacturer;
    /**
     * 设备端口
     */
    private int devicePort;
    /**
     * 网路端口
     */
    private int netPort;
    /**
     * 访问视频流用户名
     */
    private String username;
    /**
     * 访问视频流密码
     */
    private String password;
    /**
     * 备注描述
     */
    private String description;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 有效值(是否可用)
     */
    private Boolean enabled;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 摄像头分组编号
     * 411424 01 01 000001

     * 对应
     * 省市县 街道 路口 序号
     */
    private String number;
    /**
     * 是否是虚拟相机
     */
    private int isVirtual;
    /**
     * 地址
     */
    private String address;
    /**
     * 相机所在场所id
     */
    private String placeId;

    /**
     * 相机所在场所名称
     */
    private String placeName;

    private CameraGroup cameraGroup;
    private String groupName;

    public CameraInfo(int id, String groupId, String name, String ip, String longitude, String latitude, int direction,
                      String manufacturer, int devicePort, int netPort, String username, String password,
                      String description, Date addTime, Boolean enabled, String number, String placeId,String groupName) {
        this.id = id;          //
        this.groupId = groupId;
        this.name = name;
        this.ip = ip;
        this.longitude = longitude;
        this.latitude = latitude;
        this.direction = direction;
        this.manufacturer = manufacturer;
        this.devicePort = devicePort;
        this.netPort = netPort;
        this.username = username;
        this.password = password;
        this.description = description;
        this.addTime = addTime;
        this.enabled = enabled;
        this.number = number;
        this.placeId = placeId;
        this.groupName =groupName;
    }

    @Override
    public String toString() {
        return "CameraInfo{" +
                "id=" + id +
                ", cameraId='" + cameraId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", rtspUrl='" + rtspUrl + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", direction=" + direction +
                ", manufacturer='" + manufacturer + '\'' +
                ", devicePort=" + devicePort +
                ", netPort=" + netPort +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", addTime=" + addTime +
                ", enabled=" + enabled +
                ", createTime='" + createTime + '\'' +
                ", number='" + number + '\'' +
                ", isVirtual=" + isVirtual +
                ", address='" + address + '\'' +
                ", placeId='" + placeId + '\'' +
                ", cameraGroup=" + cameraGroup +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}