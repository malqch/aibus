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
public class EventTrafficSearchApiVo {

//    private String vinCode; // 采集车辆VIN

//    private String companyLineName; // 公交线路

    private String areaName; // 地区名称

    private String numberPlate; // 违规车牌号

    private String trafficViolationType; // 违章类型

    private String image1; // 证据照片1

    private String image2; // 证据照片2

    private String image3; // 证据照片3

    private double longitude; // 经度

    private double latitude; // 纬度

    private String address; // 违章地点

    private Date happenTime;// 发生时间

    public EventTrafficSearchApiVo() {
    }
}
