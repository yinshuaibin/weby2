package com.ferret.task.service;


import com.ferret.task.TaskInfo;

import java.util.Date;
import java.util.List;

public interface TaskInfoService {
    /**
     * 存储任务
     * @param task
     */
    void saveTask(TaskInfo task);


    /**
     * 存储任务
     * @param tasks
     */
    int saveTasks(List<TaskInfo> tasks);

    /**
     * 删除任务
     * @param taskId
     */
    void deleteTask(String taskId);

    /**
     * 获取所有等待状态的任务
     * @return
     */
    List<TaskInfo> getAllWaitingTasks();

    /**
     * 根据任务状态查询所有任务
     * @param state  任务状态
     * @param curPage 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    List<TaskInfo> getTaskByState(Integer state, Integer curPage, Integer pageSize);

    /**
     * 根据任务状态查询所有任务总数
     * @param state  任务状态
     * @return
     */
    int getTaskByStateCount(Integer state);

    /**
     * 修改任务进度
     * @param task
     */
    void updateTask(TaskInfo task);

    /**
     * 根据id查询对应的数据
     * @param id
     * @return
     */
    TaskInfo findOneById(String id);

    /**
     * 根据任务id,修改数据库字段,达到开始任务的功能
     * @param id  任务id
     * @return
     */
    int updateTaskRunning(String id);

    /**
     * 根据任务id,修改数据库字段,达到取消任务的功能
     * @param id  任务id
     * @return
     */
    int updateTaskcancel(String id);

    /**
     * 根据任务id，将数据库对应任务数据设置为成功状态
     * @param id
     * @return
     */
    int updateTaskSuccess(String id);

    /**
     * 根据任务id,修改任务状态
     * @param id 任务id
     * @param state 任务状态
     * @return
     */
    int updateTaskState(String id, int state);

    /**
     * 根据时间段获取该时间段内所有创建的任务
     * @param startTime
     * @param endTime
     * @return
     */
    List<TaskInfo> getTaskInfoByCreateTime(Date startTime, Date endTime, Integer pageNum, Integer pageSize);

    /**
     * 根据时间段获取该时间段内所有创建的任务总数
     * @param startTime
     * @param endTime
     * @return
     */
    int getTaskInfoByCreateTimeCount(Date startTime, Date endTime);


    /**
     * 获取所有任务
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<TaskInfo> getAllTaskInfo(Integer pageNum, Integer pageSize);

    /**
     * 获取所有任务总数
     * @return
     */
    int getAllTaskInfoCount();

    /**
     * 根据id查询任务详情
     */
    String getDescriptionsById(String taskId);
}
