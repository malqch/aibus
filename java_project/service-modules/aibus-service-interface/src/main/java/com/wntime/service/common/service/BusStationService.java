package com.wntime.service.common.service;

import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.vo.BusStationVo;
import com.wntime.service.common.vo.InfoBusStationVo;

import java.util.List;
import java.util.Map;

public interface BusStationService {
    List<BusStationVo> getStationsByCompanyAndLine(Map<String,Object> params);

    Long getBusStationIdByName(Long areaId, String stationName);

    LineStationInfoVo queryBusStationInfo(long busStationId,Long companyLineId);

    List<InfoBusStationVo> queryBusStations();
}
