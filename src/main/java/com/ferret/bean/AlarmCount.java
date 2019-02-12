package com.ferret.bean;

import lombok.Data;

@Data
public class AlarmCount {
    private String groupID; //布控分组ID
    private String groupName; //布控分组名称
    private int count; //报警数量
}
