package com.wntime.advert.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.IntegerToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 79448
 * @date 2020/8/26 11:57
 */
@Setter
@Getter
@ApiModel
public class AdvertisingStatisticsDataVo {
    @ApiModelProperty(value = "x轴数据，线路名称列表")
    private List<Integer> dataY;
    @ApiModelProperty(value = "y轴数据，覆盖人数")
    private List<String> dataX;
    @ApiModelProperty(value = "覆盖人数最大值")
    @JsonSerialize(using = IntegerToStringSerialize.class)
    private int max;

    public AdvertisingStatisticsDataVo() {
        this.dataY=new ArrayList<>();
        this.dataX=new ArrayList<>();
    }

    public void addDataY(int value){
        this.dataY.add(value);
    }
    public void addDataX(String name){
        this.dataX.add(name);
    }
}
