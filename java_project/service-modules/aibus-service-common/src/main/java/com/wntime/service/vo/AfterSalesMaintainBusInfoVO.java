package com.wntime.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ysc
 * 2020/8/29 10:14
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AfterSalesMaintainBusInfoVO extends AfterSalesBusInfoVO {

    private Long maintenanceId;
    private String maintenanceDesc;
    private Date maintenanceTime;
}
