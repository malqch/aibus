package com.wntime.ec.module.schedule.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DsmVehicleStatusRspVo {

    private Long dateTime;

    //纬度
    private Long latitude;

    //经度
    private Long longitude;

    private double speed;
}
