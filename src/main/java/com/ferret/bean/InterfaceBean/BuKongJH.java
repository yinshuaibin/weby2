package com.ferret.bean.InterfaceBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 布控实体类
 * y 1224
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuKongJH {
    private String id;

    private int status;//状态码 0:不生效,1:生效

    private String imagePath;//图片路径

    private String contorltype;//布控分组id

    private String similar;//相似度分值

    private String businessid;//唯一业务id

    private String name;//姓名

    private String sex;//性别 0:女 1:男

    private String cardnumber;//身份证号

    private int conage;//年龄

    private String conreason;//布控原因

    private String address;//住址

    private int userId;//布控人员

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//数据库中拿出来的创建时间

    private String remark;//备注

    private String groupName;//布控分组名称(前台展示使用)

    private String imageData;//图片base64(添加布控时使用)

    private String fileName;//图片文件名(添加布控时使用)
}
