package com.ferret.task.controller;

import com.ferret.task.TaskInfo;
import com.ferret.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 查询正在执行的任务
     * @return
     */
    @RequestMapping(value = "getRuningTask", method = RequestMethod.GET)
    public ResponseEntity<?> getRuningTask(){
        return ResponseEntity.ok(taskService.getRunningTask());
    }

    /**
     * 取消正在执行的任务
     * @param id
     * @return
     */
    @RequestMapping(value = "cancelRuningTask/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> cancelRuningTask(@PathVariable("id") String id){
        taskService.cancelRuningTask(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 取消正在排队的任务
     * @param id
     * @return
     */
    @RequestMapping(value = "cancelWaitingTask/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> cancelWaitingTask(@PathVariable("id") String id){
        taskService.cancelWaitingTask(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据状态查询任务
     * @param state
     * @param curPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getTaskByState/{state}/{curPage}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskByState(@PathVariable("state") Integer state, @PathVariable("curPage") Integer curPage, @PathVariable("pageSize") Integer pageSize){
        return ResponseEntity.ok(taskService.getTaskByState(state,curPage,pageSize));
    }

    /**
     * 查询碰撞进度
     * @return
     */
    @RequestMapping(value = "getProgress", method = RequestMethod.GET)
    public Double getProgerss(){
        return taskService.getProgress();
    }
}
