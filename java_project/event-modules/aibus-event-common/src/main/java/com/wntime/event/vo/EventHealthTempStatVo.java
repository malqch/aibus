package com.wntime.event.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author 79448
 * @date 2020/8/25 18:55
 */
@Setter
@Getter
public class EventHealthTempStatVo {

    private long busId;

    private String vinCode;

    private double longitude;

    private double latitude;

    private long companyLineId;

    private String companyLineName;

    private int overPersonCount;

    private String busStationId;

    private String busStationName;

    private String eventDetailId;

    public EventHealthTempStatVo() {
    }
}
