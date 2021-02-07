package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 17:14
 */
@Setter
@Getter
@ApiModel
public class BusRealtimeMonitorVo {

    @ApiModelProperty("公交车id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long busId;

    @ApiModelProperty("电池百分比")
    private double batteryEnergyRatio;

    @ApiModelProperty("电机状态")
    private int busMotorStatus;

    @ApiModelProperty("车速")
    private double busSpeed;

    @ApiModelProperty("行驶总里程")
    private double busTotalMile;

    @ApiModelProperty("剩余行驶里程")
    private double surplusMile;

    @ApiModelProperty("当前精度")
    private double factoryLongitude;

    @ApiModelProperty("当前维度")
    private double factoryLatitude;

    @ApiModelProperty("车辆状态")
    private int busStatus;

    @ApiModelProperty("电压")
    private double busBatteryVoltage;

    @ApiModelProperty("电流")
    private double busBatteryCurrent;

    @ApiModelProperty("电池温度")
    private double busBatteryTemperature;

    @ApiModelProperty("电池状态")
    private int busBatteryStatus;

}
