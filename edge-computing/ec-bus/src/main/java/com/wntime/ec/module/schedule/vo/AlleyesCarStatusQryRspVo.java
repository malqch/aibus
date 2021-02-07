package com.wntime.ec.module.schedule.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/8/29 15:31
 * @desc
 */
@Data
public class AlleyesCarStatusQryRspVo {
    String bus_battery_current;
    String bus_battery_energy;
    String bus_battery_status;
    String bus_battery_temperature;
    String bus_battery_voltage;
    String bus_motor_status;
    String bus_status;
    String latitude;
    String longitude;
    String speed;
    String total_mile;
    String surplus_mile;
}
