package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusAlarmInfo;
import com.wntime.event.region.BusMonitorSummary;
import com.wntime.event.repo.BusAlarmInfoRepository;
import com.wntime.event.repo.BusMonitorSummaryRepository;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 干扰驾驶
 */
@Component
public class InterferenceDriveEvent extends AbstractEventHandle implements EventDealAlgorithm {
    @Resource
    private GemFireCache gemFireCache;
    @Resource
    private BusAlarmInfoRepository busAlarmInfoRepository;
    @Resource
    private BusMonitorSummaryRepository busMonitorSummaryRepository;

    @Override
    public R deal(EventReportForm eventReportForm) {
        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(new BusDateKey(eventReportForm.getBusId()));
        BusAlarmInfo busAlarmInfo = assembleBusAlarmInfo(eventReportForm);
        CacheTransactionManager txManager = gemFireCache.getCacheTransactionManager();
        try {
            txManager.begin();
            optional.ifPresent(summary -> {
                summary.incrementInterferenceDriveCount();
                busMonitorSummaryRepository.save(new Wrapper<>(summary, summary.getKey()));
            });

            busAlarmInfoRepository.save(new Wrapper<>(busAlarmInfo, busAlarmInfo.getKey()));
            setBusAlarmStatus(eventReportForm.getBusId());
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
