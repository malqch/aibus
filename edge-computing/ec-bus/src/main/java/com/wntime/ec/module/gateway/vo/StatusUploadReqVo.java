package com.wntime.ec.module.gateway.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/8/28 16:53
 * @desc
 */
@Data
public class StatusUploadReqVo {
    private Long busDeviceId;
    private Long busId;
    private Long deviceTypeId;
    private Integer status;
}
