package com.ferret.docker;

/**
 * 数据解析器
 */
public interface Parser {
    /**
     * 数据解析
     * @param data 数据
     * @param type 数据类型
     * @return
     */
    String parse(String data, String type);
}
