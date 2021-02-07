package com.wntime.event.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 79448
 * @date 2020/8/25 18:55
 */
@Setter
@Getter
public class EventTrafficStatVo {

    private String eventDetailId;

    private long busId;

    private String vinCode;

    private String plateCode;

    private String busCode;

    private long companyLineId;

    private String companyLineName;

    private String eventTypeName;

    private String eventTypeCode;

    private String eventTargetName;

    private String eventTargetCode;

    private String numberPlate;

    private double longitude;

    private double latitude;

    private String address;

    private String busStationId;

    private String busStationName;

    private String img1;

    private String img2;

    private String img3;

    private List<String> evidenceImageList = new ArrayList<>();

    private Date happenTime;

    public EventTrafficStatVo() {
    }
}
