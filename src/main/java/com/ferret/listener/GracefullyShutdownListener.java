package com.ferret.listener;

import com.ferret.utils.FeatureFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 监听tomcat关闭事件,优雅退出程序
 *
 * @author cheng;
 * @date 2018/4/19;
 */
@Slf4j
@Component
public class GracefullyShutdownListener implements ApplicationListener<ContextClosedEvent>, TomcatConnectorCustomizer {

    @Autowired
    private FeatureFileUtils featureFileUtils;

    private volatile Connector connector;

    private final Integer waitSeconds = 30;

    @Bean
    public TomcatEmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addConnectorCustomizers(new GracefullyShutdownListener());
        return tomcat;
    }

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        if (connector != null) {
            // 停止web连接
            connector.pause();
            // 处理程序关闭前的相关操作
            shutdownBeforeDestroy();
            // 关闭连接池,等待所有线程执行完毕
            Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                try {
                    if (!threadPoolExecutor.awaitTermination(waitSeconds, TimeUnit.SECONDS)) {
                        log.warn("tomcat 即将在{}s内关闭,正在关闭中.........", waitSeconds);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{
            // 处理程序关闭前的相关操作
            shutdownBeforeDestroy();
        }
    }


    private void shutdownBeforeDestroy() {
        log.warn("正在保存数据.............");
        featureFileUtils.saveFeatureAndInfo(FeatureFileUtils.map);
    }
}
