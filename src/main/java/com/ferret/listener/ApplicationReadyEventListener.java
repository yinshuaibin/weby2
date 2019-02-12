package com.ferret.listener;

import com.ferret.utils.FeatureFileUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 * @author cc;
 * @since 2018/4/17;
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {


    @Autowired
    private FeatureFileUtils featureFileUtils;

    /*@Value("${facelibpath}")
    String faceLibPath;*/

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("------------------ 项目启动完成 --------------------------");
        //featureFileUtils.readAfterReady();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
