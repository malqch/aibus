package com.wntime.fault.service;

import com.wntime.fault.vo.*;

import java.util.Collection;
import java.util.List;

public interface FaultInfoService {

    Collection<FaultInfoVo> queryBusFaultLevelStatistics(long busId);

    List<InfoCollectFaultVo> queryCollectFault();

    List<InfoFaultExtendVo> queryFaultExtend();

    List<InfoFaultLevelVo> queryFaultLevel();

    List<InfoFaultTargetVo> queryFaultTarget();

    List<InfoFaultTypeVo> queryFaultType();

    List<BusFaultInfoVo> queryBusFaultInfos(long busId);
}
