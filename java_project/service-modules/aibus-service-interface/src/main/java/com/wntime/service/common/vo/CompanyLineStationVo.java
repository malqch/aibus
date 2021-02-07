package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/5 10:54
 */
@Setter
@Getter
public class CompanyLineStationVo {

    private long companyId;

    private long companyLineId;

    private String companyLineName;

    private String companyLineCode;

    private long busStationId;

    private String busStationName;

    private int stationOrder;

    private double busStationLatitude;

    private double busStationLongitude;

}
