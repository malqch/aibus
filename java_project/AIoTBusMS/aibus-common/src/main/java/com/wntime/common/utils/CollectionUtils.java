package com.wntime.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public class CollectionUtils {

    /**
     * list转成map结构：
     * {fieldName: list}
     * @param list
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> Map<String,List<T>> turnListToMapList(List<T> list, String fieldName){
        if(list == null || list.size()<=0){
            return null;
        }
        Map<String,List<T>> mapList = new HashMap<String, List<T>>();
        for(T obj : list){
            try {
                Object fieldValue = FieldMethodUtil.invokeGetter(obj, fieldName, false);
                if(fieldValue!=null){
                    String key = fieldValue.toString();
                    if(StringUtils.isEmpty(key)){
                        continue;
                    }
                    if(!mapList.containsKey(key)){
                        mapList.put(key, new ArrayList<T>());
                    }
                    mapList.get(key).add(obj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapList;
    }

    /**
     * list转成map结构：
     * {fieldName: list}
     * @param list
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> Map<String,List<T>> turnListToLinkedHashMapList(List<T> list, String fieldName){
        if(list == null || list.size()<=0){
            return null;
        }
        Map<String,List<T>> mapList = new LinkedHashMap<String, List<T>>();
        for(T obj : list){
            try {
                Object fieldValue = FieldMethodUtil.invokeGetter(obj, fieldName, false);
                if(fieldValue!=null){
                    String key = fieldValue.toString();
                    if(StringUtils.isEmpty(key)){
                        continue;
                    }
                    if(!mapList.containsKey(key)){
                        mapList.put(key, new ArrayList<T>());
                    }
                    mapList.get(key).add(obj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapList;
    }

    /**
     * list转成map结构：
     * {fieldName1: {fieldName2: obj}}
     * @param list
     * @param fieldName1 大类
     * @param fieldName1 小类
     * @param <T>
     * @return
     */
    public static <T> Map<String,Map<String,T>> turnListToMap2(List<T> list, String fieldName1,String fieldName2){
        if(list == null || list.size()<=0){
            return null;
        }
        Map<String,Map<String,T>> returnMap = new HashMap<String, Map<String,T>>();
        for(T obj : list){
            try {
                Object fieldValue1 = FieldMethodUtil.invokeGetter(obj, fieldName1, false);
                Object fieldValue2 = FieldMethodUtil.invokeGetter(obj, fieldName2, false);
                if(fieldValue1 != null){
                    String key1 = fieldValue1.toString();
                    String key2 = fieldValue2.toString();
                    if(!returnMap.containsKey(key1)){
                        Map<String,T> map = new HashMap<String, T>();
                        map.put(key2,obj);
                        returnMap.put(key1,map);
                    }else{
                        returnMap.get(key1).put(key2,obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnMap;
    }
}
