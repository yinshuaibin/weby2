package com.ferret.service.identify;

import com.ferret.task.Task;
import com.ferret.task.TaskInfo;

import java.util.Date;

public class IdentityTask implements Task {
    private IdentityChecker identityChecker;
    private Date startTime,endTime;
    private TaskInfo taskInfo;
    public IdentityTask(IdentityChecker identityChecker, Date startTime, Date endTime){
        this.identityChecker = identityChecker;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskInfo = new TaskInfo();
        taskInfo.setDescription("身份落地任务，数据起始时间:" + startTime.toString() + "数据结束时间:" + endTime.toString());
    };
    @Override
    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    @Override
    public int execute() {
        identityChecker.check(startTime, endTime);
        return 0;
    }

    @Override
    public void cancel() {

    }

    @Override
    public String getTaskID() {
        return taskInfo.getId();
    }

    @Override
    public double progress() {
        return 0.0;
    }
}
