package com.wntime.service.common.service;

import com.wntime.service.common.vo.BusInfoMotorTypeVo;
import com.wntime.service.common.vo.BusInfoTypeVo;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
public interface BusTypeInfoService {

    BusInfoTypeVo queryByCode(String code);
}
