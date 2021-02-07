package com.wntime.service.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/31 14:16
 */
@Setter
@Getter
public class UploadDeviceVo extends DeviceUploadItem{

    private Long busId;
    private String deviceName;
}
