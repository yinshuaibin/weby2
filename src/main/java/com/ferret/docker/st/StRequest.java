package com.ferret.docker.st;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ferret.docker.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * st数据请求实现
 */
@Component("request")
public class StRequest implements Request {
    @Autowired
    private StConfigure stConfigure;
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public String request(Map<String, String> params, String type) {
        System.out.println("call stRequest ...");
        JSONObject jsonObject = stConfigure.getFormatByType(type);
        if(jsonObject != null) {
            String url = this.createURL(params, jsonObject);
            // 网络请求
            try {
                restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                String resultString = restTemplate.getForObject(url, String.class);
                System.out.println(url);
                return resultString;
            } catch (RestClientException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成请求URL
     * @param ps
     * @param jo
     * @return
     */
    private String createURL(Map<String,String> ps , JSONObject jo) {
        JSONArray jsonArray = jo.getJSONArray(StConfigure.CONTEXT);
        String type = jo.getString(StConfigure.DATA_TYPE);
        if(type.equals("cg")) type = "";
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<jsonArray.size();i++) {
            String item = jsonArray.getJSONObject(i).getString(StConfigure.ITEM_KEY);
            sb.append((type.isEmpty()? item : type + "." + item) + ";");
        }
        String url = stConfigure.getUrl() + "?";
        for(Map.Entry<String, String> e : ps.entrySet()) {
            url += e.getKey() + "=" + e.getValue() + "&";
        }
        url += "cx=" + sb.substring(0, sb.length()-1);
        return url;
    }
}
