package com.wntime.service.common.service;

import com.wntime.service.common.vo.*;

import java.util.Collection;
import java.util.List;

public interface CompanyLineService {

    BusLineVo queryBusLineInfo(long busId);

    List<BusLineVo> queryCompanyBusLineInfo(long busCompanyId);

    BusLineVo queryBusLine(long busId);

    List<BusOperationVo> queryCompanyLines(long companyId, long companyLineId, String keyWord);

    List<BusLineVo> queryCompanyLineList(long companyId);

    List<LineStationVo> queryCompanyLines();

    List<BusLinePlanServiceVo> queryBusLinePlanServices(Long busId);

    Collection<CompanyLineOverviewVo> queryCompanyLineOverview(long companyId);

    List<BusLineStationVo> queryCompanyLineStations(long companyId,String companyLineName);

    List<CompanyLineNameVo> queryCompanyLineNames(long companyId);

    Long queryCompanyLineBusId(long companyId);

	List<Long> queryCompanyLineByCodeAndCompany(String companyLineCode,Long companyId);

	void checkCompanyLine(long companyLineId);

    int queryCompanyLineBusCount(long companyLineId);
}

