package com.wntime.maintain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ysc
 * 2020/8/26 19:55
 */

@Data
public class FaultBusVO {

    private Long busId;
    private String vin;
    private float voltage;
    private float current;
    private String faultLevelCode;
    private String faultLevelName;
    private Date faultTime;
    private Long motorStatus;
}
