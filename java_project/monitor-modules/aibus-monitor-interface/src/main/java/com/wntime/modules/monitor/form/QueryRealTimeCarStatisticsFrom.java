package com.wntime.modules.monitor.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel
@Setter
@Getter
public class QueryRealTimeCarStatisticsFrom {

    @NotNull(message = "汽车厂商id不能为空")
    @ApiModelProperty(notes = "汽车厂商id",required = true)
    private Long factoryId;
    @ApiModelProperty(notes = "vin号、车牌号、公交公司车辆编号")
    private String keyword;
    @ApiModelProperty(notes = "查询车辆数量",required = true)
    @Positive(message = "数据条数必须大于0")
    private int size;

}
