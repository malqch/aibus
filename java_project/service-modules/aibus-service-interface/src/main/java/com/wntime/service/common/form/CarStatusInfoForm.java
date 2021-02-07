package com.wntime.service.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
@ApiModel
public class CarStatusInfoForm {

    @PositiveOrZero(message = "车速不能为负数")
    @NotNull(message = "车速不能为空")
    @Max(value = 260,message = "最高时速应小于260")
    @ApiModelProperty(value = "车速",required = true,dataType = "java.long.Double",allowEmptyValue = false)
    private Double busSpeed;

    @PositiveOrZero(message = "总里程不能为负数")
    @NotNull(message = "总里程不能为空")
    @ApiModelProperty(value = "总里程",required = true,dataType = "java.long.Double")
    private Double busTotalMile;

    @PositiveOrZero(message = "剩余里程不能为负数")
    @NotNull(message = "剩余里程不能为空")
    @ApiModelProperty(value = "剩余里程",required = true,dataType = "java.long.Double")
    private Double surplusMile;

    @NotNull(message = "经度不能为空")
    @ApiModelProperty(value = "经度",required = true,dataType = "java.long.Double")
    private Double factoryLongitude;

    @NotNull(message = "纬度不能为空")
    @ApiModelProperty(value = "纬度",required = true,dataType = "java.long.Double")
    private Double factoryLatitude;

    @Min(value = 0,message = "车辆状态只能为0|1")
    @Max(value = 1,message = "车辆状态只能为0|1")
    @NotNull(message = "车辆状态不能为空")
    @ApiModelProperty(value = "车辆状态",required = true,dataType = "java.long.Double")
    private Integer busStatus;

    @PositiveOrZero(message = "电压值必须大于零")
    @NotNull(message = "电压不能为空")
    @ApiModelProperty(value = "电压",required = true,dataType = "java.long.Double")
    private Double busBatteryVoltage;

    @Max(value = 100,message = "电量最大值为100")
    @PositiveOrZero(message = "电量不能为负数")
    @NotNull(message = "电量不能为空")
    @ApiModelProperty(value = "电量",required = true,dataType = "java.long.Double")
    private Double busBatteryEnergy;

    @PositiveOrZero(message = "电流不能为负数")
    @NotNull(message = "电流不能为空")
    @ApiModelProperty(value = "电流",required = true,dataType = "java.long.Double")
    private Double busBatteryCurrent;

    @NotNull(message = "电池温度不能为空")
    @ApiModelProperty(value = "电池温度",required = true,dataType = "java.long.Double")
    private Double busBatteryTemperature;

    @Min(value = 0,message = "电池状态只能为0|1|2|3")
    @Max(value = 3,message = "电池状态只能为0|1|2|3")
    @NotNull(message = "电池状态不能为空")
    @ApiModelProperty(value = "电池状态",required = true,dataType = "java.long.Long")
    private Integer busBatteryStatus;

    @Min(value = 0,message = "电机状态只能为0|1|2|3")
    @Max(value = 3,message = "电机状态只能为0|1|2|3")
    @NotNull(message = "电机状态不能为空")
    @ApiModelProperty(value = "电机状态",required = true,dataType = "java.long.Long")
    private Integer busMotorStatus;
 }
