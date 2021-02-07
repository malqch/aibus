package com.wntime.fault.vo;

import java.util.ArrayList;
import java.util.List;

public class VerValueData{
    private List<Integer> data = new ArrayList<>();
    private String type = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
