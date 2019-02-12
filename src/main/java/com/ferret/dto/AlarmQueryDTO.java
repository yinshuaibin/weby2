package com.ferret.dto;

import lombok.Data;

import java.util.Date;

/**
 * 实时报警查询的查询字段的实体类
 * @author cc;
 * @since 2018/2/8;
 *
 */
@Data
public class AlarmQueryDTO {
    /**
     * 选择的摄像头编号
     */
    private String[] cameraNumbers;

    /**
     * 起始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 布控分组
     */
    private String groupId;

    /**
     * 布控原因,文字匹配
     */
    private String reason;
    /**
     * 布控id
     */
    private Integer bkId;
    /**
     * 分页信息,如果需要分页
     */
    private Integer pageNum;
    private Integer pageSize;
}
