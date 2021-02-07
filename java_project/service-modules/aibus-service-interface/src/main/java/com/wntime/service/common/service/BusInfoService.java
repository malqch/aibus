package com.wntime.service.common.service;

import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.service.common.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
public interface BusInfoService {

    BusBaseInfoVo queryBusInfo(long busId);

    List<BusInfoVo> queryWithFuzzyMatch(long factoryId,String keyWord,int size);

    BusRealtimeStatisticsVo queryBusMonitorStatistics(long busId);

    List<InfoBusEntity> getAllBusByAreaId(Long areaId, List<Long> companyIdList);

    List<InfoBusEntity> getAllBusByAreaIdListAndCompanyId(List<Long> areaIdList, List<Long> companyIdList);

    List<InfoBusEntity> getAllBusByQueryAreaId(Map<String, Object> params);

    List<InfoBusEntity> getAllBusByCompanyId(Long companyId);

    BusDrivingBaseDataVo queryBusDrivingData(long busId);

    List<BusStatusVO> getBusStatusList();

    BusStatusVO getBusStatusByCode(String busStatusCode);

    BusStatusVO getBusStatusById(Long busStatusId);

    int changeBusStatus(Long busId, Long busStatusId);

    void updateBusCount(long busId,String status);


    Map<String,Object> queryBusStatusSummary(long busCompanyId);

    void updateBusCompany(long busId,long companyId);

    void saveBusInfo(BusBaseInfoVo busBaseInfoVo);

    InfoBusEntity saveImportBusInfo(OrderBusDeliveryBatchForm orderBusDeliveryBatchForm);

    void incrementFaultBusStatusSummary(long companyId);

    void incrementNormalBusStatusSummary(long companyId);

    void updateBusInfoStatus(long busId,int busStatus);

    void decrementBusStatusSummaryCount(long companyId,long busId);

    void checkBusNormal(long busId);
}
