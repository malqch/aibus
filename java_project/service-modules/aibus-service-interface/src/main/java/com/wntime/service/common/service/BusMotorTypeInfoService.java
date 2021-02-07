package com.wntime.service.common.service;

import com.wntime.service.common.vo.BusInfoBatteryTypeVo;
import com.wntime.service.common.vo.BusInfoMotorTypeVo;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
public interface BusMotorTypeInfoService {

    BusInfoMotorTypeVo queryByCode(String code);
}
