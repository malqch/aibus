package com.wntime.service.common.service;

import com.wntime.service.common.vo.BusDeviceStatusVo;

import java.util.Map;

public interface BusDeviceService {

    Map<String, BusDeviceStatusVo> queryBusDeviceStatus(long busId);
}
