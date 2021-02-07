package com.wntime.service.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.service.common.vo.BusPassengerAgeVo;
import com.wntime.service.common.vo.BusPassengerVo;
import com.wntime.service.common.vo.CompanyLinePassengerVo;

import java.util.List;

public interface BusServiceLogService extends IService<LogBusServiceEntity> {

    void saveBusServiceLog(LogBusServiceEntity logBusServiceEntity);

    List<CompanyLinePassengerVo> queryTodayBusServiceLog(long companyId);

    void createBusServiceLog(long busId, LineStationInfoVo lineStationInfoVo);

    void disableLatestBusServiceLog(long busId,long companyLineId,long stationId,Long nextStationId);

    BusPassengerVo queryTodayBusPassenger(long busId);

    BusPassengerAgeVo queryTodayBusPassengerAge(long busId);

    int queryBusKeepRide(long busId,long companyLineId);

    void removeBusDriveStation(long busId);
}
