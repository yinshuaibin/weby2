package com.ferret.utils.staticUtils;
import java.util.List;
 
import net.sf.json.JSONArray;
 
public class JsonListUtil {
    /**
     * List<T> 转 json 保存到数据库
     */
    public static <T> String listToJson(List<T> ts) {
    	JSONArray jsonarray = JSONArray.fromObject(ts);  
        return jsonarray.toString();
    }
 
    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        JSONArray jsonarray = JSONArray.fromObject(jsonString);
        @SuppressWarnings("unchecked")
		List<T> ts = (List<T>)JSONArray.toCollection(jsonarray, clazz);  
 
        return ts;
    }
 
}