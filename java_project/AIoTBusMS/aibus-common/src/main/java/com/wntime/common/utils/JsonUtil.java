package com.wntime.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * json解析工具类
 * 请补充
 */
public class JsonUtil {
    
    private static final SerializeConfig config;
    
    private static final ValueFilter filter = new ValueFilter() {
        public Object process(Object object, String name, Object value) {
            if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                return new BigDecimal(value.toString());
            }
            return value;
        }
    };
    
    static {  
        config = new SerializeConfig();
        config.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格式
    }  
  
    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.DisableCircularReferenceDetect
    };  
  
    public static String toJSONString(Object object) {  
        return JSON.toJSONString(object, config, features);
    }  
      
    public static String toJSONNoFeatures(Object object) { 
        return JSON.toJSONString(object, config,filter);
    }  
    
    private Map<String, Object> bean2Map(String text){
        return JSONObject.parseObject(text, new TypeReference<Map<String, Object>>(){});
    }
  
  
    public static Object toBean(String text) {  
        return JSON.parse(text);
    }  
  
    public static <T> T toBean(String text, Class<T> clazz) {  
        return JSON.parseObject(text, clazz);
    }  
  
    // 转换为数组  
    public static <T> Object[] toArray(String text) {  
        return toArray(text, null);  
    }  
  
    // 转换为数组  
    public static <T> Object[] toArray(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz).toArray();
    }  
  
    // 转换为List  
    public static <T> List<T> toList(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz);
    }  
  
    /**  
     * 将javabean转化为序列化的json字符串  
     * @param keyvalue  
     * @return  
     */  
    public static Object beanToJson(Object keyvalue) {  
        String textJson = JSON.toJSONString(keyvalue);
        Object objectJson  = JSON.parse(textJson);
        return objectJson;  
    }  
      
    /**  
     * 将string转化为序列化的json字符串  
     * @param keyvalue  
     * @return  
     */  
    public static Object textToJson(String text) {  
        Object objectJson  = JSON.parse(text);
        return objectJson;  
    }  
      
    /**  
     * json字符串转化为map  
     * @param s  
     * @return  
     */  
    public static JSONObject String2Json(String s) {
        JSONObject m = JSONObject.parseObject(s);
        return m;  
    }
      
    /**  
     * 将map转化为string  
     * @param m  
     * @return  
     */  
    public static String collectToString(Map m) {  
        String s = JSONObject.toJSONString(m);
        return s;  
    }  
}
