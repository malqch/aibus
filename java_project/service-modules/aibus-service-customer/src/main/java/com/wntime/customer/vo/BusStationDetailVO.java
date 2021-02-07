package com.wntime.customer.vo;

import lombok.Data;

/**
 * @author ysc
 * 2020/8/28 8:56
 */
@Data
public class BusStationDetailVO {

    private Long busStationId;
    private String busStationName;
    private String companyLines;
    private Integer busCount;
    private Double busStationLongitude;
    private Double busStationLatitude;
    private Double pm_2_5;
}
