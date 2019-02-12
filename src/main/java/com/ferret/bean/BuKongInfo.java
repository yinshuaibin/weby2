package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 布控信息表(tb_bukong_info),关联tb_bukong表
 * @author cc
 * @since 2018-02-01
 * @version v1.0
 */

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuKongInfo {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 证件号码
     */
    private String idcard;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 关系人,表示亲戚/夫妻/父子女/兄弟等关系
     */
    private String relative;

    /**
     * 临时住所
     */
    private String residence;

    /**
     * 房间号码
     */
    private String roomNumber;

    /**
     * 临时住所类型
     */
    private String residenceType;

    /**
     * 房东idcard
     */
    private String landlordIdcard;

    /**
     * 职业
     */
    private String job;

    /**
     * 是否有便民卡
     */
    private String convenienceCardExisted;

    /**
     * 是否暂住证
     */
    private String tempResidenceExisted;

    /**
     * 暂住证号码
     */
    private String tempResidenceNumber;

    /**
     * 从哪儿来,什么时间
     */
    private String fromAndWhen;

    /**
     * 到哪儿去,什么时间
     */
    private String goAndWhen;

    /**
     * 使用的交通工具
     */
    private String transport;

    /**
     * 核查日期
     */
    private Date dateChecked;

    /**
     * 核查警员名称
     */
    private String policeChecked;

    /**
     * 备注或评语说明
     */
    private String comment;

    /**
     * 图片地址,临时增加,备用
     * @author cheng
     * @since 2017-02-07
     */
    private String imagePath;

}