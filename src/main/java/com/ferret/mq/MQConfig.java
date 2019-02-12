package com.ferret.mq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mq配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MQConfig {
    private String ip; // 目标ip
    private int port; // 端口
    private String username; // 用户名
    private String password; // 密码
    private int connectTimeOut; // 连接超时
    private int keepAlive; // 保持连接时间
}
