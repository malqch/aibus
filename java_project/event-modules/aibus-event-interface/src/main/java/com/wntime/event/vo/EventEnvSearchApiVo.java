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
public class EventEnvSearchApiVo {

    private String areaName; // 地区名称

    private String busStationName; // 站点名称

    private String type; // 环境数据类型（temperature、humidity、pm25、pm10）

    private double value; // 具体环境信息值

    private double longitude; // 经度

    private double latitude; // 纬度

    private Date happenTime;// 采集时间

    public EventEnvSearchApiVo() {
    }
}
