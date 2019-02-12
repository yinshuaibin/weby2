package com.ferret.docker.st;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.docker.Holder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
@Component
public class StConfigure {
    @Value("${st.url}")
    private String url;
    @Value("${st.formatDir}")
    private String formatDir;

    public static  final String CONTEXT ="data";
    public static final String DATA_TYPE = "type";
    public static final String ITEM_KEY = "key";
    public static final String ITEM_DICTYPE = "dicType";
    private Holder<Map<String, JSONObject>> fmtsHolder = new Holder<>();

    public JSONObject getFormatByType(String type) {
        Map<String, JSONObject> map = fmtsHolder.get();
        if(map == null) {
            synchronized (fmtsHolder) {
                if(map == null) {
                    loadFormat();
                }
            }
        }
        return fmtsHolder.get().get(type);
    }
    private void loadFormat() {
        BufferedReader br = null;
        try {
            Map<String, JSONObject> fmts = new HashMap<>();
            FileInputStream fis = new FileInputStream(formatDir);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8" );
            br = new BufferedReader(isr);
            String lineText = null;
            while ((lineText=br.readLine())!=null){
                System.out.println(lineText);
                if(lineText.length()>0){
                    JSONObject jo = JSON.parseObject(lineText);
                    if(jo.containsKey(DATA_TYPE)) {
                        fmts.put(jo.getString(DATA_TYPE), jo);
                    }
                }
            }
            fmtsHolder.set(fmts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if( br != null){
                    br.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return url;
    }

}
