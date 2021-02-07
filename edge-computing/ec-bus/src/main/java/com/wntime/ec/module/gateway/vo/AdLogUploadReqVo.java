package com.wntime.ec.module.gateway.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/11/18 15:16
 * @desc
 */
@Data
public class AdLogUploadReqVo {
    private Long advertiseDeliveryId;
    private Long advertisePositionId;
    private int attachType;
    private Long busId;
    private Long companyLineId;
    private int showTimes;
}
