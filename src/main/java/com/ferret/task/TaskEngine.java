package com.ferret.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务执行引擎
 */
@Component
public class TaskEngine implements Task , Runnable{

    private volatile boolean taskExit = false;
    // 解决注入失败问题
    @PostConstruct
    public void init(){
        instance = this;
    }

    private TaskEngine(){
        thread = new Thread(this);
        thread.start();
    }
    private static TaskEngine instance; // 实例
    private static List<Task> tasks = new ArrayList<>();
    private Task curTask = null;
    private Thread thread = null;
    // 获取当前队列任务数量
    public int getTaskCount() {
        return tasks.size();
    }
    public static TaskEngine getInstance(){
        if(null == instance) {
            synchronized (TaskEngine.class) {
                if(null == instance) {
                    instance = new TaskEngine();
                }
            }
        }
        return instance;
    }
    // 添加任务
    public void addTask(Task task) {
        System.out.println("create task " +task.getTaskInfo().getDescription());
        tasks.add(task);
    }
    @Override
    public TaskInfo getTaskInfo() {
        return curTask!=null?curTask.getTaskInfo(): null;
    }

    //执行任务
    @Override
    public int execute() {
        if(curTask != null) {
            // 有正在执行的任务
            return TaskInfo.TaskState.RUNNING;
        }
        if(tasks.size()<=0) {
            // 队列中待执行任务
            return TaskInfo.TaskState.WAITING;
        }
        // 获取队列第一个任务
        curTask = tasks.get(0);
        //taskInfoService.updateTaskRunning(curTask.getTaskID());
        if(curTask == null) {
            return TaskInfo.TaskState.WAITING;
        }
        TaskInfo taskInfo = curTask.getTaskInfo();
        taskInfo.setTaskState(TaskInfo.TaskState.RUNNING);
        taskInfo.setStartTime(new Date());
        try{
            int state = curTask.execute();
            if(state == TaskInfo.TaskState.SUCCESS) {
                // 执行成功
                curTask.getTaskInfo().setTaskState(TaskInfo.TaskState.SUCCESS);
            }
            return state;
        } catch (Exception e) {
            // 执行出错
            e.printStackTrace();
            taskInfo.setTaskState(TaskInfo.TaskState.FAILD);
            taskInfo.setFinishTime(new Date());
            return TaskInfo.TaskState.FAILD;
        } finally {
            // 执行移除队列操作
            synchronized (tasks){
                tasks.remove(0);
            }
            curTask = null;
        }
    }
    //停止任务
    @Override
    public void cancel() {
        if(curTask != null) {
            // 停止当前任务
            curTask.cancel();
            //修改数据库中字段
            curTask.getTaskInfo().setTaskState(TaskInfo.TaskState.CANCELED);
            curTask.getTaskInfo().setFinishTime(new Date());
        }
    }

    @Override
    public String getTaskID() {
        return curTask!=null?curTask.getTaskID(): null;
    }

    /**
     * 取消等待中的任务
     * @param id
     */
    public void cancelWaitingTask(String id){
        synchronized (tasks) {
            for(int i=0;i<tasks.size();i++){
                if(tasks.get(i).getTaskID().equals(id)){
                    tasks.remove(i);
                    return;
                }
            }
        }
    }

    // 返回当前正在执行的任务进度
    @Override
    public double progress() {
        return curTask!=null?curTask.progress(): -1;
    }
    @Override
    public void run() {
        while (!taskExit) {
            this.execute();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //停止任务
    public void stop() {
        if(curTask != null) {
            // 停止当前任务
            curTask.cancel();
            //修改数据库中字段
            curTask.getTaskInfo().setTaskState(TaskInfo.TaskState.WAITING);
            curTask.getTaskInfo().setFinishTime(new Date());
        }
        taskExit = true;
    }
}
