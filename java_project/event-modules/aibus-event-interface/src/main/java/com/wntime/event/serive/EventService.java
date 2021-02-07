package com.wntime.event.serive;

import com.wntime.event.vo.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EventService {

    Map<String, DriverViolationVo> driverViolationMap(long busId);

    List<PassengerFlowVo> queryPassengerFlowInfo(long busId);

    BusPassengerStatisticsVo queryPassengerFlowInfoAnalysis(long busId);

    List<HarmfulAlarmVo>  queryHarmfulAlarmInfo(long busId);

    LineFullSeatRateRankVo queryCompanyLineFullSeatRateRank(long companyId,int size);

    Collection<BusAlarmInfoVo> queryBusAlarmInfos(long busId);


    List<PassengerGenderStatisticsVo> queryPassengerGenderFlowInfoAnalysis(long factoryId);

    int queryPassengerSum(long factoryId);
}
