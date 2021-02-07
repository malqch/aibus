package com.wntime.service.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ysc
 * 2020/9/1 17:01
 */
@Data
@AllArgsConstructor
public class LineChartData {

    private List<String> textList;
    private List<Double> valueList;
}
