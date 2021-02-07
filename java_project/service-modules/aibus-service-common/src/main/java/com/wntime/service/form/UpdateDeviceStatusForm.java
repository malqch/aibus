package com.wntime.service.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel
public class UpdateDeviceStatusForm {

    @NotNull(message = "公交车id不能为空")
    @ApiModelProperty(value = "公交车id",required = true,dataType = "long")
    private Long busId;

    @NotNull(message = "设备id不能为空")
    @ApiModelProperty(value = "设备id",required = true,dataType = "long")
    private Long busDeviceId;
    @NotNull(message = "设备类型id不能为空")
    @ApiModelProperty(value = "设备类型id",required = true,dataType = "long")
    private Long deviceTypeId;

    @Min(value = 0,message = "状态值只能为0|1")
    @Max(value = 1,message = "状态值只能为0|1")
    @ApiModelProperty(value = "设备状态 0 离线 1在线 ",required = true,dataType = "int")
    private int status;

}
