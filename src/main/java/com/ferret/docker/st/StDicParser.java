package com.ferret.docker.st;

import com.ferret.dao.DicParser;
import com.ferret.docker.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * st字典数据解析
 */
@Component
public class StDicParser implements Parser {

    @Autowired
    private DicParser dicParser;

    @Override
    public String parse(String data, String type) {
        String name = dicParser.getName(data,type);
        if(name == null) {
            return data;
        }
        return name;
    }
}
