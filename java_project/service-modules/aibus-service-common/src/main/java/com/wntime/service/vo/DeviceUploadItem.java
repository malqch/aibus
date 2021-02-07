package com.wntime.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author 79448
 * @date 2020/8/31 14:04
 */
@Setter
@Getter
@ApiModel
public class DeviceUploadItem {
    @ApiModelProperty("公交车设备id，不填写表示新增")
    @NotNull(message = "公交车设备id不能为空")
    @Positive(message = "公交车设备Id必须大于零")
    private Long busDeviceId;
    @ApiModelProperty(value = "设备编号",required = true)
    @NotNull(message = "设备编号不能为空")
    private String deviceCode;
    @ApiModelProperty(value = "设备描述编号",required = true)
    @NotNull(message = "设备描述编号不能为空")
    private String deviceDescCode;
    @ApiModelProperty(value = "设备类型id",required = true)
    @NotNull(message = "设备类型id不能为空")
    @Positive(message = "设备类型id必须大于0")
    private Long deviceTypeId;
    @ApiModelProperty(value = "设备状态 0|1 离线|在线",required = true)
    @Min(value = 0,message = "状态只能为0|1")
    @Max(value = 1,message = "状态只能为0|1")
    private int deviceStatus;
    @ApiModelProperty(value = "设备名称",required = true)
    @NotNull(message = "设备名称不能为空")
    private String deviceName;
}
