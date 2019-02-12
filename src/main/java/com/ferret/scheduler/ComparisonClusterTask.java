package com.ferret.scheduler;

import com.ferret.service.identify.IdentityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class ComparisonClusterTask {

    @Value("${identityCheck.checkerType}")
    private String chekerType;
    @Autowired
    private IdentityFactory identityFactory;
    /**
     * 每2分钟执行一次，处理tb_cluster_comparison表
     */
    //cron = "0 0 23 * * ?" //每天23点执行一次vcron = "0 0/2 * * * ?"//每2分钟执行一次 cron = "*/5 * * * * ?"//每5秒执行一次
    @Scheduled(cron = "0 0/2 * * * ?")
    public void operateCluster(){
        try {
            //只有完善信息方式为省厅的时候,才需要开启此定时任务中的处理
            if (chekerType.equals("st")) {
                //comparisonClusterService.getclusterComparison();
                identityFactory.getIdentityChecker().check();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkIdentity(){
        Date endTime = new Date();
        Date startTime = new Date(endTime.getTime() - 3600 * 1000 * 24L);
        identityFactory.getIdentityChecker().check(startTime, endTime);
    }
}
