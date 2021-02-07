package com.wntime.fault.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author psl
 * 2020/8/26 11:26
 */
public class FaultColumnStatData {
    private List<String> textList = new ArrayList<>();
    private List<Integer> valueList = new ArrayList<>();
    private int total;
    private String name;
    public FaultColumnStatData(){

    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    public List<Integer> getValueList() {
        return valueList;
    }

    public void setValueList(List<Integer> valueList) {
        this.valueList = valueList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
