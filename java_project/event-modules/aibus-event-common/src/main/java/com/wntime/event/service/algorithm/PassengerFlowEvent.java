package com.wntime.event.service.algorithm;

//import com.wntime.advert.service.AdvertiseService;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusLineFullSeatRate;
import com.wntime.event.region.BusLineFullSeatRateKey;
import com.wntime.event.region.BusMonitorSummary;
import com.wntime.event.region.PassengerSummary;
import com.wntime.event.repo.BusLineFullSeatRateRepository;
import com.wntime.event.repo.BusMonitorSummaryRepository;
import com.wntime.event.repo.PassengerSummaryRepository;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.BusServiceLogService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.vo.BusBaseInfoVo;
import lombok.Getter;
import lombok.Setter;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

/**
 * 车内人流事件
 *
 * @author 79448
 * @date 2020/8/25 17:01
 */
@Component
public class PassengerFlowEvent extends AbstractEventHandle implements EventDealAlgorithm {

    @Resource
    private GemFireCache gemFireCache;
    @Resource
    private BusServiceLogService busServiceLogService;

    @Resource
    private BusMonitorSummaryRepository busMonitorSummaryRepository;

    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;

    @Resource
    private BusLineFullSeatRateRepository busLineFullSeatRateRepository;

    @Resource
    private PassengerSummaryRepository passengerSummaryRepository;

    @Resource
    private BusInfoService busInfoService;

//    @Resource
//    private AdvertiseService advertiseService;

    @Resource
    private CompanyLineService companyLineService;

    @Override
    public R deal(EventReportForm eventReportForm) {

        String targetCode = eventReportForm.getEventTargetCode();
        Long busId = eventReportForm.getBusId();
        BusDateKey key = new BusDateKey(busId);
        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(key);
        BusMonitorSummary summary = optional.orElse(new BusMonitorSummary(key));

        Optional<BusRealtimeMonitorStatistics> sOptional = busRealtimeMonitorStatisticsRepository.findById(key);
        BusRealtimeMonitorStatistics statistics = sOptional.orElse(new BusRealtimeMonitorStatistics(key));
        Map<String, Object> map = eventReportForm.getTags();
        Object busStationId = map.get("bus_station");
        Object busLine = map.get("bus_line");
        LineStationInfoVo stationVo = handleGPSConvert(busStationId,busLine, eventReportForm.getEventDate(), eventReportForm.getEventDetailId());
        BusLineFullSeatRate fullSeatRate=null;
        int busCount=0;
        if (stationVo != null) {
            BusLineFullSeatRateKey rateKey = new BusLineFullSeatRateKey(stationVo.getCompanyLineId());
            Optional<BusLineFullSeatRate> optional2 = busLineFullSeatRateRepository.findById(rateKey);
            fullSeatRate = getBusLineFullSeatRate(stationVo, optional2);
            fullSeatRate.setKey(rateKey);
            busCount=companyLineService.queryCompanyLineBusCount(stationVo.getCompanyLineId());
        }
        BusBaseInfoVo bus = busInfoService.queryBusInfo(busId);

        Optional<PassengerSummary> summaryOptional = passengerSummaryRepository.findById(bus.getFactoryId());
        PassengerSummary passengerSummary =summaryOptional.orElse(new PassengerSummary(bus.getFactoryId()));



        PassengerCount passengerCount = new PassengerCount();

        int maleCount = 0, femaleCount = 0, childrenCount = 0, adultCount = 0, oldCount = 0;
        switch (targetCode) {
            case "get_on"://上车
                Object gender = map.get("gender");
                if ("male".equals(gender)) {
                    maleCount = 1;
                } else if ("female".equals(gender)) {
                    femaleCount = 1;
                }
                Object ageObj = map.get("age");
                if (ageObj != null && ageObj !="") {
                    if ("1".equals(ageObj)) {
                        childrenCount = 1;
                    } else if ("2".equals(ageObj)) {
                        adultCount = 1;
                    } else if ("3".equals(ageObj)) {
                        oldCount = 1;
                    }
                }
/*                Object male = map.get("male");
                if (male != null) {
                    maleCount = (int) male;
                }
                Object female = map.get("female");
                if (female != null) {
                    femaleCount = (int) female;
                }
                Object children = map.get("children");
                if (children != null) {
                    childrenCount = (int) children;
                }
                Object adult = map.get("adult");
                if (adult != null) {
                    adultCount = (int) adult;
                }
                Object old = map.get("old");
                if (old != null) {
                    oldCount = (int) old;
                }*/
                if (maleCount > 0) {
                    summary.addMaleCount(maleCount);
                    passengerCount.setMaleNum(maleCount);
                    passengerSummary.incrementMale();
                }
                if (femaleCount > 0) {
                    summary.addFemaleCount(femaleCount);
                    passengerCount.setFemaleNum(femaleCount);
                    passengerSummary.incrementFemale();
                }
                if (childrenCount > 0) {
                    summary.addChildrenCount(childrenCount);
                    passengerCount.setChildrenNum(childrenCount);
                    passengerSummary.incrementChild();
                }
                if (adultCount > 0) {
                    summary.addAdultCount(adultCount);
                    passengerCount.setAdultNum(adultCount);
                    passengerSummary.incrementAdult();
                }
                if (oldCount > 0) {
                    summary.addOldCount(oldCount);
                    passengerCount.setOldMun(oldCount);
                    passengerSummary.incrementOld();
                }
                summary.setBoardNum(summary.getBoardNum() + 1);
                int boardNum = summary.getChildrenCount() + summary.getAdultCount()+summary.getOldCount();
                statistics.setAiFace(boardNum);
                int psCount=summary.getBoardNum()-summary.getDownNum();
                summary.setPassengerCount(psCount>0 ? psCount:0);
                passengerCount.setBusGetOn(1);
                passengerSummary.incrementPassenger();
                summary.setStationBoardNum(summary.getStationBoardNum()+1);
                break;
            case "get_off"://下车
                Object downNum = map.get("number");
                if (downNum != null && downNum !="") {
                    int downNumber=(int) downNum;
                    if(downNumber>0){
                        summary.setStationDownNum(summary.getStationDownNum()+ downNumber);
                        int psCountO= summary.getPassengerCount();
                        if(downNumber>psCountO){downNumber=psCountO;}
                        summary.incrementDownNum(downNumber);
                        int psc = summary.getBoardNum() - summary.getDownNum();
                        summary.setPassengerCount(psc>0?psc:0);
                        passengerCount.setBusGetOff(downNumber);
                    }
                }
                break;
            case "close_door"://关门 车内
                //设置公交车的满座率
                Object count = map.get("number");
                if (count != null && count!="") {
                    int countC=(int) count;
                    if(countC>=0){
                        if(countC !=summary.getPassengerCount()){
                            countC=summary.getPassengerCount();
                        }
                        summary.setPassengerCount(countC);
                        statistics.setPassengerCount(countC);
                        if(fullSeatRate!=null) {
                            fullSeatRate.calculateFullSeatRateAvg(statistics.getFullSeatRate(),busCount);
                        }
                        passengerCount.setBusKeepRide(countC);
                    }
                }
                break;
        }
        int pasCount = summary.getBoardNum() - summary.getDownNum();
        pasCount=pasCount>0?pasCount:0;
        statistics.setPassengerCount(pasCount);
        if(fullSeatRate!=null) {
            fullSeatRate.calculateFullSeatRateAvg(statistics.getFullSeatRate(),busCount);
        }
        passengerCount.setBusKeepRide(statistics.getPassengerCount());

        generateBusServiceLog(eventReportForm, stationVo, passengerCount);
        //增加此公交车上播放的广告的覆盖人数
//        if(busLine !=null){
//            advertiseService.increaseAdvertiseShowLogPeopleCount(busId,Long.valueOf(String.valueOf(busLine)));
//        }

        CacheTransactionManager txManager = gemFireCache.getCacheTransactionManager();
        try {
            txManager.begin();
            busMonitorSummaryRepository.save(new Wrapper<>(summary, summary.getKey()));
            busRealtimeMonitorStatisticsRepository.save(new Wrapper<>(statistics, statistics.getKey()));
            txManager.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (txManager.exists()) {
                txManager.rollback();
            }
        }
        if (fullSeatRate != null) {
            busLineFullSeatRateRepository.save(new Wrapper<>(fullSeatRate, fullSeatRate.getKey()));
        }
        passengerSummaryRepository.save(passengerSummary);
        return R.ok();
    }

    private BusLineFullSeatRate getBusLineFullSeatRate(LineStationInfoVo lineVo, Optional<BusLineFullSeatRate> optional) {
        BusLineFullSeatRate data;
        if (optional.isPresent()) {
            data = optional.get();
        } else {
            data = new BusLineFullSeatRate();
            data.setCompanyLineCode(lineVo.getCompanyLineCode());
            data.setCompanyId(lineVo.getCompanyId());
            data.setCompanyLineName(lineVo.getCompanyLineName());
        }
        return data;
    }

    public void generateBusServiceLog(EventReportForm eventReportForm, LineStationInfoVo stationVo, PassengerCount passengerCount) {
        if (stationVo == null) {return;}
        //查询上一个站点的营运日志，记录完后将其置为不可用

        LogBusServiceEntity logBusServiceEntity = new LogBusServiceEntity();
        logBusServiceEntity.setCreatedDate(new Timestamp(eventReportForm.getEventDate()));
        logBusServiceEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
        logBusServiceEntity.setBusId(eventReportForm.getBusId());
        logBusServiceEntity.setCompanyLineId(stationVo.getCompanyLineId());
        logBusServiceEntity.setCompanyLongitude(stationVo.getBusStationLongitude());
        logBusServiceEntity.setCompanytLatitude(stationVo.getBusStationLatitude());
        logBusServiceEntity.setBusStationId(stationVo.getBusStationId());
        logBusServiceEntity.setNextStationId(stationVo.getNextStationId());

        logBusServiceEntity.setBusGetOn(passengerCount.getBusGetOn());
        logBusServiceEntity.setBusGetOff(passengerCount.getBusGetOff());
        logBusServiceEntity.setBusKeepRide(passengerCount.getBusKeepRide());
        logBusServiceEntity.setChildrenNum(passengerCount.getChildrenNum());
        logBusServiceEntity.setAdultNum(passengerCount.getAdultNum());
        logBusServiceEntity.setOldMun(passengerCount.getOldMun());

        logBusServiceEntity.setMaleTotal(passengerCount.getMaleNum());
        logBusServiceEntity.setFemaleTotal(passengerCount.getFemaleNum());
        logBusServiceEntity.setChildrenTotal(passengerCount.getChildrenNum());
        logBusServiceEntity.setAdultTotal(passengerCount.getAdultNum());
        logBusServiceEntity.setOldTotal(passengerCount.getOldMun());



        busServiceLogService.saveBusServiceLog(logBusServiceEntity);
    }

}

@Setter
@Getter
class PassengerCount {

    private int busGetOn;
    /**
     * @desc 下车人数
     */
    private int busGetOff;
    /**
     * @desc 乘车人数
     */
    private int busKeepRide;
    /**
     * @desc 儿童人数
     */
    private int childrenNum;
    /**
     * @desc 成年人数
     */
    private int adultNum;
    /**
     * @desc 老年人数
     */
    private int oldMun;

    private int maleNum;

    private int femaleNum;
}
