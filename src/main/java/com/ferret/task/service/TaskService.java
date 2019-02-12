package com.ferret.task.service;

import com.ferret.task.TaskInfo;

import java.util.Map;

public interface TaskService {

    /**
     *  查询正在运行的任务
     */
    TaskInfo getRunningTask();

    /**
     *  取消正在运行的任务
     */
    void cancelRuningTask(String id);

    /**
     *  取消正在等待的任务
     */
    void cancelWaitingTask(String id);

    /**
     *  根据任务状态查询任务
     *  0等待，1执行中，2取消，3执行成功，4执行失败，其他未知状态
     */
    Map getTaskByState(Integer state, Integer curPage, Integer pageSize);

    /**
     *  查询碰撞进度
     */
    Double getProgress();

    /**
     * 根据时间段查询碰撞任务
     */
    Map getTaskByTime(TaskInfo taskInfo);
}
