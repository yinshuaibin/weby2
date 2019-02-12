package com.ferret.docker;

import java.util.Map;

public interface Request {
    /**
     * 数据请求接口
     * @param params 参数
     * @param type 数据类型
     * @return
     */
    String request(Map<String, String> params, String type);
}
