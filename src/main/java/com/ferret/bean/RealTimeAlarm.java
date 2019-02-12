package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * 实现了Cloneable接口,重写了克隆方法
 * 修改人:y
 * 修改时间:0702
 * 修改原因:报警推送需要新加一个list集合数据,此数据中第一条和第二条数据除了部分属性不同以外,其余相同,list.add时引用地址,导致这两条不应该一样的数据
 * 一样了,所以用clone()方法,并且增加了一个标记:flage,前台根据此标记区分这两个数据
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RealTimeAlarm implements Cloneable{
    /** 主键ID */
    private BigInteger id;
    /** 报警id*/
    private BigInteger alarmId;
    /** 布控人员id */
    private BigInteger bkId;
    /** 布控分组id */
    private Integer bkGroupId;
    /** 姓名 */
    private String name;
    /** 性别 */
    private Integer sex;
    /** 身份证号 */
    private String  cardNumber;
    /** 报警图片表Id */
    private BigInteger alarmPicId;
    /** 处警状态 0:未处警 1:已处警 */
    private Integer status;
    /** 处警类型 1: 排除2: 怀疑 3:确认 */
    private Integer handleType;
    /** 出警备注 */
    private String handlereMark;
    /** 相似度*/
    private String similar;
    /** 报警时间*/
    private String alarmTime;
    /** 出警人员ID */
    private BigInteger managerId;
    /** 出警人员姓名 */
    private String managerName;
    /** 出警时间 */
    private String manageTime;
    /** 布控图片路径*/
    private String bkImagePath;
    /** 报警图片路径*/
    private String alarmImagePath;
    /** 摄像ID*/
    private Integer cameraId;
    /** 背景图地址 */
    //@JsonSerialize(using = Path2UrlSerialize.class)
    private String backgroundImagePath;
    /** 报警特征图片存储路径 */
    private String peoplepicpath;
    /** 虹软识别分数 */
    private String arcscore;
    /** 项目id */
    private String projectId;
    /** 描述信息 */
    private String remark;
    /************ 报警相关的几个额外字段,根据real_time_alarm表内的对应字段查询的  **************/
    /**
     * 报警摄像头名称,根据camera_id字段查询
     */
    private String cameraName;
    /**
     * 布控原因
     */
    private String reason;
    /**  人员类型id   */
    private Integer controltypeid;
    
	//经纬度   -y 0716
	private String longitude;
	private String latitude;

	/** 布控表中写入的姓名 */
	private String bkName;
    /** 布控人身份证号 */
    private String idCard;

	//需要的报警条数
	private Integer alarmSize;
	
	private boolean flage;
    //重写Object类的clone方法
    public Object clone() {
        Object obj=null;
        //调用Object类的clone方法，返回一个Object实例
        try {
            obj= super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}