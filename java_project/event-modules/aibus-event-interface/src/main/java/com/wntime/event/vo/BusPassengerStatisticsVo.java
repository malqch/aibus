package com.wntime.event.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 79448
 * @date 2020/8/28 19:42
 */
@Setter
@Getter
@ApiModel
public class BusPassengerStatisticsVo {

    @ApiModelProperty("男乘客数量")
    private int maleNum;
    @ApiModelProperty("女乘客数量")
    private int femaleNum;
    @ApiModelProperty("年龄段乘客列表")
    private List<PassengerFlowVo> ageAnalysis;

}
