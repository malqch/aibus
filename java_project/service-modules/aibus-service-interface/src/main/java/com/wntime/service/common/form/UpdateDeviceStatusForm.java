package com.wntime.service.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UpdateDeviceStatusForm {
    @ApiModelProperty(value = "设备id",required = true,dataType = "long")
    private long deviceId;
    @ApiModelProperty(value = "设备类型id",required = true,dataType = "long")
    private long deviceTypeId;
    @ApiModelProperty(value = "设备状态 0 离线 1在线 ",required = true,dataType = "int")
    private int status;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
