package com.wntime.service.common.util;

import com.wntime.service.common.vo.LineChartData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author ysc
 * 2020/9/1 18:44
 */

public class LineChartUtil {

    public static <T> LineChartData creatLineChartData(List<T> dataList, Function<T, String> textFunc, Function<T, Double> valueFuc) {

        List<String> textList = new ArrayList<>();
        List<Double> valueList = new ArrayList<>();
        for (T item : dataList) {
            String name = textFunc.apply(item);
            Double data = valueFuc.apply(item);
            textList.add(name);
            valueList.add(data);
        }
        return new LineChartData(textList, valueList);
    }

    public static LineChartData creatLineChartDataFromMap(List<Map<String,Object>> dataList, String textName, String valueName) {
        return creatLineChartData(dataList,map -> (String) map.get(textName),map -> (Double)map.get(valueName));
    }
}
