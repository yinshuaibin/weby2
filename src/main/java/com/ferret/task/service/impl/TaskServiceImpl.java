package com.ferret.task.service.impl;

import com.ferret.task.TaskEngine;
import com.ferret.task.TaskInfo;

import com.ferret.task.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    /**
     * 查看正在执行的任务
     * @return
     * @author hyl 18-10-31
     */
    @Override
    public TaskInfo getRunningTask() {
        return TaskEngine.getInstance().getTaskInfo();
    }

    /**
     * 取消正在执行的任务
     * @return
     * @author hyl 18-10-31
     */
    @Override
    public void cancelRuningTask(String id) {
        TaskEngine.getInstance().cancel();
    }

    /**
     * 取消正在等待的任务
     * @return
     * @author hyl 18-10-31
     */
    @Override
    public void cancelWaitingTask(String id) {
        TaskEngine.getInstance().cancelWaitingTask(id);
    }

    /**
     * 根据状态获取任务信息
     * @return
     * @author hyl 18-10-31
     */
    @Override
    public Map getTaskByState(Integer state, Integer curPage, Integer pageSize) {
        return  null;
    }

    /**
     * 获取碰撞进度
     * @return
     */
    @Override
    public Double getProgress(){
        return TaskEngine.getInstance().progress();
    }

    /**
     * 根据创建时间查询碰撞任务并分页
     * @param taskInfo
     * @return
     */
    @Override
    public Map getTaskByTime(TaskInfo taskInfo){
        return null;
    }

    /**
     * 项目退出时执行此方法
     */
    @PreDestroy
    public void destory(){
        TaskEngine.getInstance().stop();
    }
}
