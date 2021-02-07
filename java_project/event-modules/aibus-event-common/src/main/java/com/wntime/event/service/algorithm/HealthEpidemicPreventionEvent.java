package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusAlarmInfo;
import com.wntime.event.region.BusMonitorSummary;
import com.wntime.event.repo.BusAlarmInfoRepository;
import com.wntime.event.repo.BusMonitorSummaryRepository;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * 卫生防疫事件
 *
 * @author 79448
 * @date 2020/8/25 16:55
 */
@Component
public class HealthEpidemicPreventionEvent extends AbstractEventHandle implements EventDealAlgorithm {

    @Resource
    private GemFireCache gemFireCache;
    @Resource
    private BusAlarmInfoRepository busAlarmInfoRepository;
    @Resource
    private BusMonitorSummaryRepository busMonitorSummaryRepository;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;


    @Override
    public R deal(EventReportForm eventReportForm) {

        Long busId = eventReportForm.getBusId();
        BusDateKey key = new BusDateKey(busId);
        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(key);
        BusMonitorSummary summary = optional.orElse(new BusMonitorSummary(key));
        summary.incrementBodyTemperatureAbnormalCount();

        Optional<BusRealtimeMonitorStatistics> sOptional = busRealtimeMonitorStatisticsRepository.findById(key);
        BusRealtimeMonitorStatistics statistics = sOptional.orElse(new BusRealtimeMonitorStatistics(key));
        statistics.setTemperatureAbnormal(summary.getBodyTemperatureAbnormalCount());

        Map<String, Object> tags = eventReportForm.getTags();
        Object busStationId = tags.get("bus_station");
        Object busLine = tags.get("bus_line");
        handleGPSConvert(busStationId,busLine, eventReportForm.getEventDate(), eventReportForm.getEventDetailId());
        BusAlarmInfo busAlarmInfo = assembleBusAlarmInfo(eventReportForm);
        CacheTransactionManager txManager = gemFireCache.getCacheTransactionManager();
        try {
            txManager.begin();
            busMonitorSummaryRepository.save(new Wrapper<>(summary, summary.getKey()));
            busRealtimeMonitorStatisticsRepository.save(new Wrapper<>(statistics, statistics.getKey()));
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
