package com.wntime.advert.service;

import com.wntime.advert.form.ReportAdvertiseLogForm;
import com.wntime.advert.vo.AdvertisingStatisticsDataVo;
import com.wntime.advert.vo.InfoAdvertisePositionVo;
import com.wntime.advert.vo.InfoAdvertiseTargetVo;

import java.util.List;
import java.util.Map;

/**
 * @author 79448
 * @date 2020/11/6 15:59
 */
public interface AdvertiseService {

    /**
     * 查询所有广告位
     * @return
     */
    List<InfoAdvertisePositionVo> queryPositionList();

    /**
     * 查询所有广告标签
     * @return
     */
    List<InfoAdvertiseTargetVo> queryTargetList();

    /**
     * 通过线路查询投放中的广告详情
     * @param companyLineId
     * @return
     */
    Map<String,Object> queryCompanyLineAdvertise(long companyLineId);


    void insertAdvertiseLog(ReportAdvertiseLogForm reportAdvertiseLogForm);

    void increaseAdvertiseShowLogPeopleCount(long busId,long companyLineId);

    AdvertisingStatisticsDataVo queryFactoryBusAdvertiseStatistics(long factoryId,int size);

    void checkAdvertiseDelivery(long advertiseDeliveryId);

    void checkAdvertisePosition(long advertisePositionId);
}
