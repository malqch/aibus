package com.wntime.ec.module.gateway.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/11/13 16:05
 * @desc
 */
@Data
public class Station {
    private Long busStationId;
    private Long lineStationId;
    private int index;

    public Station(Long busStationId, Long lineStationId, int index) {
        this.busStationId = busStationId;
        this.lineStationId = lineStationId;
        this.index = index;
    }
}
