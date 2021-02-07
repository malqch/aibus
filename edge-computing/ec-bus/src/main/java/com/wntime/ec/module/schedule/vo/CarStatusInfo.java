package com.wntime.ec.module.schedule.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/8/28 18:53
 * @desc
 */
@Data
public class CarStatusInfo {
    private Long busId;
    private String busBatteryCurrent;
    private String busBatteryEnergy;
    private String busBatteryStatus;
    private String busBatteryTemperature;
    private String busBatteryVoltage;
    private String busMotorStatus;
    private String busSpeed;
    private String busStatus;
    private String busTotalMile;
    private String factoryLatitude;
    private String factoryLongitude;
    private String surplusMile;

}
