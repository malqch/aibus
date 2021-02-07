package com.wntime.service.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 20:29
 */
@Setter
@Getter
public class BatteryTypeInfoVo {
    /*
     * @desc 电池电压
	 */
    private Double batteryTypeVoltage;
    /**
     * @desc 电池能量
     */
    private Double batteryTypeEnergy;
    /**
     * @desc 电池电流
     */
    private Double batteryTypeCurrent;


}
