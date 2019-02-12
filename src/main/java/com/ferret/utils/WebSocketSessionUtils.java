package com.ferret.utils;

import com.ferret.bean.User;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 保存websocket的用户session
 * @author ;cc
 * @since 2018/3/22;
 * @version 1.0
 */
public class WebSocketSessionUtils {
    private final static ConcurrentMap<String,Object> sessionMap = new ConcurrentHashMap<>();

    public static void add(String id, Object object) {
        if (object != null) {
            User addUser=(User)object;
            Set<String> keys = sessionMap.keySet();
            if(keys.size()<1){
                sessionMap.put(id,object);
            }else {
                for(String key: keys){
                    User user=(User)sessionMap.get(key);
                    if(!user.getUserId().equals(addUser.getUserId())){
                        sessionMap.put(id,object);
                    }
                }
            }

        }
    }

    public static void remove(String id) {
        if (id != null) {
            sessionMap.remove(id);
        }
    }

    public static ConcurrentMap<String, Object> getSessionMap() {
        return sessionMap;
    }

}
