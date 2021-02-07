package com.wntime.service.common.service;

import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
public interface BusBatteryTypeInfoService {

    BusInfoBatteryTypeVo queryByCode(String code);
}
