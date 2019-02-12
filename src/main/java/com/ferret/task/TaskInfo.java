package com.ferret.task;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TaskInfo {
    private String id = UUID.randomUUID().toString().replaceAll("-","");

    private int taskState = 0;

    private Date startTime;

    private Date finishTime;

    private Date createTime = new Date();

    private String taskType;

    private String description; // 任务描述


    // 任务状态
    public static class TaskState {
        public static int WAITING = 0;
        public static int RUNNING = 1; // 执行中
        public static int CANCELED = 2; // 取消
        public static int SUCCESS = 3;  // 执行成功
        public static int FAILD = 4;    // 执行失败
    }
}
