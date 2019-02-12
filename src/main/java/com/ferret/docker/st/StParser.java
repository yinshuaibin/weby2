package com.ferret.docker.st;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ferret.docker.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 省厅数据解析
 */
@Component("parser")
public class StParser implements Parser {
    @Autowired
    private Parser stDicParser;
    @Autowired
    private StConfigure stConfigure;
    @Override
    public String parse(String data, String type) {
        if(data == null) {
            return null;
        }
        JSONObject fmtJo = stConfigure.getFormatByType(type);
        if(fmtJo != null) {
            JSONObject resultJo = new JSONObject();
            String[] strings = data.split(";");
            JSONArray jsonArray = fmtJo.getJSONArray(StConfigure.CONTEXT);
            if(strings.length == jsonArray.size()) {
                for(int i=0;i<jsonArray.size();i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String dicType = jo.getString(StConfigure.ITEM_DICTYPE);
                    if(dicType.equals("")) {
                        resultJo.put(jo.getString(StConfigure.ITEM_KEY), strings[i]);
                    } else {
                        resultJo.put(jo.getString(StConfigure.ITEM_KEY),
                                stDicParser.parse(strings[i], dicType));
                    }
                }
                return resultJo.toJSONString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
