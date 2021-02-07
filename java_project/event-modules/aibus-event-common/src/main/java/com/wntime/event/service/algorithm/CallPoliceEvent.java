package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import org.springframework.stereotype.Component;

/**
 * 报警（110，120，119）事件
 * @author 79448
 * @date 2020/8/25 17:03
 */
@Component
public class CallPoliceEvent implements EventDealAlgorithm {
    @Override
    public R deal(EventReportForm eventReportForm) {
        return R.ok();
    }
}
