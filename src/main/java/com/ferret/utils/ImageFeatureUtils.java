package com.ferret.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cc;
 * @since 2018/1/17;
 */
@Component
public class ImageFeatureUtils {
    // 提取特征队列
    public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    public final static long HALF_HOUR = 30 * 60 * 1000;
    public final static String TIMER_SEPARATOR = ",";

    /**
     * 增加提取图片的特征到map中,方便后面查询时取走;
     *
     * @param value
     */
    public static String put(String value) {
        // key的格式采取uuid+时间戳,时间戳是为了方便定时任务移除对应的数据.
        String key = UUID.randomUUID().toString() + TIMER_SEPARATOR + new Date().getTime();
        map.put(key, value);
        return key;
    }

    public static String get(String key) {
        return map.getOrDefault(key, "");
    }

    public static void take(String key) {
        map.remove(key);
    }


    // 设计定时功能,每隔30分钟触发一次,移除掉可能超时的查询特征
    @Scheduled(fixedDelay = HALF_HOUR)
    public void scheduleTask() {
        map.forEach((key, value) -> {
            // 如果 key 的时间戳 是半小时之前,则移除该数据.
            Long now = new Date().getTime();
            Long timer = Long.valueOf(key.split(TIMER_SEPARATOR)[1]);
            if (now - timer > HALF_HOUR) {
                map.remove(key);
            }
        });
    }

}
