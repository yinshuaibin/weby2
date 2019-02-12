package com.ferret.socket.controller;

import com.ferret.controller.BaseController;
import com.ferret.socket.bean.MonitorCamera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket前端处理controller类,主要完成两种处理
 * 1. 通过映射websocket请求端口,收取前端发送来的数据
 * 2. 监听后端接口接收数据事件,并广播或转发到前端
 *
 * @author cc;
 * @since 2017/12/19;
 */

@RestController
public class WebSocketController extends BaseController{
//    @Autowired
//    private RealTimeMonitorServiceImpl realTimeMonitorService;

    /**
     * 注册实时监控浏览的服务.<br/>
     * 1. 浏览器发送请求通过@MessageMapping映射这个地址,并携带注册实时浏览需要的数据 <br>
     * 2. 服务端接收数据,realTimeRegister需要是json格式的数据. <br>
     * 3. 注册到实时监控浏览服务中 <br>
     * @param monitorCamera 实时监控注册实例
     * @throws Exception
     */

    @MessageMapping("/webservice/monitor")
    public void registerRealTimeMonitorService(MonitorCamera monitorCamera) {
        logger.debug("获取实时浏览注册 ------- 摄像头id: " + monitorCamera.getCameraId() + ", 用户id: " + monitorCamera.getUserId());
//        realTimeMonitorService.register(monitorCamera);
    }

    /**
     * 切换摄像头时,退订之前订阅的
     * @param monitorCamera
     * @throws Exception
     */
    @MessageMapping("/webservice/monitorQuit")
    public void unregisterRealTimeMonitorService(MonitorCamera monitorCamera) {
        logger.debug("获取实时浏览退订 ------- 摄像头id: " + monitorCamera.getCameraId() + ", 用户id: " + monitorCamera.getUserId());
//        realTimeMonitorService.unregister(monitorCamera);
    }
}
