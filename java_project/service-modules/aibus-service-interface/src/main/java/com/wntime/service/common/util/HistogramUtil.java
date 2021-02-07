package com.wntime.service.common.util;

import com.wntime.service.common.vo.HistogramData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author ysc
 * 2020/8/26 11:36
 */

public class HistogramUtil {

    public static <T> HistogramData creatHistogramData(List<T> dataList, Function<T, String> textFunc, Function<T, Integer> valueFuc) {

        List<String> textList = new ArrayList<>();
        List<Integer> valueList = new ArrayList<>();
        int total = 0;
        for (T item : dataList) {
            String name = textFunc.apply(item);
            Integer data = valueFuc.apply(item);
            textList.add(name);
            valueList.add(data);
            total += data;
        }
        return new HistogramData(textList, valueList, total);
    }

    public static HistogramData creatHistogramDataFromMap(List<Map<String,Object>> dataList, String textName, String valueName) {
        return creatHistogramData(dataList,map -> (String) map.get(textName),map -> {
            Object value = map.get(valueName);
            if(value instanceof Integer){
                return (Integer)value;
            }else {
                return ((Long)value).intValue();
            }
        });
    }
}
