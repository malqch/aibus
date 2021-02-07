package com.wntime.service.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/9/1 16:06
 */
@Setter
@Getter
@ApiModel
public class BusStatusSummaryItemVo {

    @ApiModelProperty("公交公司车辆总数")
    private int max;
    @ApiModelProperty("车辆状态代码  0正常 1 告警 2待修 3原厂")
    private int busStatus;
    @ApiModelProperty("车辆数")
    private int number;
    @ApiModelProperty("车辆状态描述")
    private String name;
}
