package com.wntime.service.common.service;

import com.wntime.service.common.vo.FactorySaleVo;
import com.wntime.service.common.vo.OrderStatisticsVo;

import java.util.List;

public interface BusOrderService {
    List<OrderStatisticsVo> queryFactoryOrderStatistic(long factoryId);

    int queryFactoryOrderSum(long factoryId);

    List<FactorySaleVo> queryFactorySalesVolume(long factoryId);
}
