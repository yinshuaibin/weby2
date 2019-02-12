package com.ferret.dto;

import com.ferret.bean.AlarmCount;
import lombok.Data;

import java.util.List;
/**
 * 根据时间段查询布控分组下报警信息的实体类
 * @author hyl;
 * @since 2018/10/12;
 *
 */
@Data
public class AlarmPersonDTO {
    //布控分组
    private List<AlarmCount> groupIds;

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;
}
