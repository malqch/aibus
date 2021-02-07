package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/2 17:09
 */
@Setter
@Getter
public class InfoBusStationVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long busStationId;

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long areaId;

    /**
     * @desc 公交车站名称
     */
    private String busStationName;
    /**
     * @desc 公交车站编码
     */
    private String busStationCode;
    /**
     * @desc 经度
     */
    private Double busStationLongitude;
    /**
     * @desc 纬度
     */
    private Double busStationLatitude;
    /**
     * @desc 偏差(米)
     */
    private Integer busStationDeviation;
}
