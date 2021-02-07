package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import org.springframework.stereotype.Component;

/**
 * 打架斗殴事件
 *
 * @author 79448
 * @date 2020/8/25 16:55
 */
@Component
public class FightingEvent extends AbstractEventHandle implements EventDealAlgorithm {
    @Override
    public R deal(EventReportForm eventReportForm) {
        setBusAlarmStatus(eventReportForm.getBusId());
        return R.ok();
    }
}