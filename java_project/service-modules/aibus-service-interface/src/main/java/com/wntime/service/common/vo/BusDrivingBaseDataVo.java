package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author 79448
 * @date 2020/8/27 14:57
 */
@Setter
@Getter
@ApiModel
public class BusDrivingBaseDataVo {

    @ApiModelProperty("行驶里程")
    private double busTotalMile;
    @ApiModelProperty("当前车速")
    private double busSpeed;
    @ApiModelProperty("电量百分比")
    private double batteryEnergyRatio;
    @ApiModelProperty("电机状态")
    private int busMotorStatus;

    @JsonSerialize(using = LongToStringSerialize.class)
    private long busId;

    private String vinCode;

    private String companyLineName;

    private String plateCode;

}
