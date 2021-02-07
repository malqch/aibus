package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import org.springframework.stereotype.Component;

/**
 * 司机情绪
 */
@Component
public class DriverMoodEvent extends AbstractEventHandle implements EventDealAlgorithm {





    @Override
    public R deal(EventReportForm eventReportForm) {
        setBusAlarmStatus(eventReportForm.getBusId());
        return R.ok();
    }
}