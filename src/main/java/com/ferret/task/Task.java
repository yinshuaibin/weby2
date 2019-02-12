package com.ferret.task;


public interface Task {
    TaskInfo getTaskInfo();
    // 执行任务
    int execute();
    // 结束任务
    void cancel();
    String getTaskID();
    // 获取任务执行进度 0-1
    double progress();

}
