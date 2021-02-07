package com.wntime.event.service.algorithm;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusAlarmInfo;
import com.wntime.event.region.DriverViolation;
import com.wntime.event.region.DriverViolationStatistics;
import com.wntime.event.repo.BusAlarmInfoRepository;
import com.wntime.event.repo.DriverViolationRepository;
import com.wntime.event.repo.DriverViolationStatisticsRepository;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.event.vo.DriverViolationEvent;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

/**
 * 驾驶违规
 */
@Component
public class DriveOffenceEvent extends AbstractEventHandle implements EventDealAlgorithm {


    @Resource
    private GemFireCache gemFireCache;
    @Resource
    private BusAlarmInfoRepository busAlarmInfoRepository;
    @Resource
    private DriverViolationRepository driverViolationRepository;
    @Resource
    private DriverViolationStatisticsRepository driverViolationStatisticsRepository;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;

    @Override
    public R deal(EventReportForm eventReportForm) {

        Long busId = eventReportForm.getBusId();
        DriverViolation driverViolation = new DriverViolation();
        driverViolation.setId(IdWorker.getId());
        driverViolation.setBusId(busId);
        driverViolation.setEventTargetId(eventReportForm.getEventTypeId());
        driverViolation.setEventTargetName(eventReportForm.getEventTargetName());
        driverViolation.setEventTargetCode(eventReportForm.getEventTargetCode());
        driverViolationRepository.save(driverViolation);
        int eventDetail = eventReportForm.getEventDetail();


        BusAlarmInfo busAlarmInfo = assembleBusAlarmInfo(eventReportForm);
        Optional<DriverViolationStatistics> violationStatisticOptional = driverViolationStatisticsRepository.findById(busAlarmInfo.getKey());

        Optional<BusRealtimeMonitorStatistics> sOptional = busRealtimeMonitorStatisticsRepository.findById(busAlarmInfo.getKey());
        CacheTransactionManager txManager = gemFireCache.getCacheTransactionManager();
        try {
            txManager.begin();
            if(eventDetail ==1 || eventDetail==2 ||eventDetail==3 ||eventDetail ==4){
                violationStatisticOptional.ifPresent(violationStatistic->{
                    Set<DriverViolationEvent> events = violationStatistic.getEvents();
                    events.add(new DriverViolationEvent(eventReportForm.getEventTypeId(),eventReportForm.getEventTargetName(),eventReportForm.getEventTargetCode()));
                    driverViolationStatisticsRepository.save(new Wrapper<>(violationStatistic,violationStatistic.getKey()));
                });
            }

            sOptional.ifPresent(statistics -> {
                if (eventDetail == 1) {//疲劳驾驶
                    statistics.incrementFatigueDriver();
                    busRealtimeMonitorStatisticsRepository.save(new Wrapper<>(statistics, statistics.getKey()));
                } else if (eventDetail == 4) {//分神驾驶
                    statistics.incrementDistracted();
                    busRealtimeMonitorStatisticsRepository.save(new Wrapper<>(statistics, statistics.getKey()));
                }
            });

            busAlarmInfoRepository.save(new Wrapper<>(busAlarmInfo, busAlarmInfo.getKey()));
            setBusAlarmStatus(busId);
            txManager.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (txManager.exists()) {
                txManager.rollback();
            }
        }
        return R.ok();
    }
}