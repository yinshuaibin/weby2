package com.ferret.task;

public interface TaskBuilder {
    Task build(TaskInfo taskInfo);
    String getTaskType();
}
