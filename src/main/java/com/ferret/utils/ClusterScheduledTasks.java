package com.ferret.utils;

import com.ferret.bean.ClusterWei;
import com.ferret.dao.ClusterWeiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 读取陌生维族人的定时工具
 *
 * @author cc;
 * @version 1.0
 * @since 2018/4/11;
 */
@Slf4j
@Component
public class ClusterScheduledTasks {

    @Autowired
    private ClusterWeiMapper clusterWeiMapper;
    // 线程安全的队列
    private static List<ClusterWei> list = Collections.synchronizedList(new LinkedList<>());

    // 创建定时器，每隔两秒读一次tb_cluster_weizu 表，获取2秒内的新增数据,并保存起来
    //@Scheduled(fixedRate = 2000)
    private void listRecentRecords() {
        list = clusterWeiMapper.listRecentRecords();
    }

    public static List<ClusterWei> get() {
        return list;
    }
}
