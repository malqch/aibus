package com.wntime.event.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author 79448
 * @date 2020/8/27 17:13
 */
@Setter
@Getter
@ApiModel
public class LineFullSeatRateRankVo {

    @ApiModelProperty(value = "线路满座率列表")
    private List<Integer> dataY;
    @ApiModelProperty(value = "线路名称列表")
    private List<String> dataX;
    @ApiModelProperty(value = "满座率最大值")
    private double max = 1;

    public LineFullSeatRateRankVo() {
        this.dataY = new ArrayList<>();
        this.dataX = new ArrayList<>();
    }

    public void addDataY(Integer value) {
        this.dataY.add(value);
    }

    public void addDataX(String name) {
        this.dataX.add(name);
    }

    public void setLimit(int limit) {
        if (this.dataX.size() > limit && limit > 1) {
            this.dataX = this.dataX.subList(0, limit - 1);
            this.dataY = this.dataY.subList(0, limit - 1);
        }
    }

    public void sort(){
        Map<String,Integer> temp =new TreeMap<>();
        for(int i=0;i< dataY.size();i++){
            temp.put(dataX.get(i),dataY.get(i));
        }
        this.dataY.clear();
        this.dataX.clear();
        temp.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                .reversed()).forEachOrdered(e->{this.dataY.add(e.getValue());this.dataX.add(e.getKey());});
    }

}
