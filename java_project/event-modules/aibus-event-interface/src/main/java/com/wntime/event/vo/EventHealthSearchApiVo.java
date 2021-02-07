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
public class EventHealthSearchApiVo {

    private String vinCode; // 采集车辆VIN

    private String areaName; // 地区名称

    private String companyLineName; // 公交线路

    private String busStationName; // 站点名称

    private Long temperature; // 体温

    private String image; // 采集体温照片

    private double longitude; // 经度

    private double latitude; // 纬度

    private Date happenTime; // 发生时间

    public EventHealthSearchApiVo() {
    }
}
