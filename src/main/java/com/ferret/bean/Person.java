package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Person {
    // 对应数据库 jh_person表
    private String personid;
    private String representimgpath;
    private String cardnumberpicpath;
    private String cardnumber;
    private String name;
    private String sex;
    private String address;
    private String isuse;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    private Integer count;
    private String clusterid;
}
