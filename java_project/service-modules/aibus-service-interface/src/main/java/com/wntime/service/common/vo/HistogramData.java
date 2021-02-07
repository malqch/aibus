package com.wntime.service.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author ysc
 * 2020/8/26 11:26
 */
@Data
@AllArgsConstructor
public class HistogramData {

    private List<String> textList;
    private List<Integer> valueList;
    private int total;
}
