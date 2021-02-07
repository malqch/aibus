package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/29 17:12
 */
@Setter
@Getter
@ApiModel
public class BusDeviceStatusVo {


    @ApiModelProperty("设备id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备状态， 0离线 1在线")
    private int status;

    @ApiModelProperty("设备代码")
    private String deviceCode;
}
