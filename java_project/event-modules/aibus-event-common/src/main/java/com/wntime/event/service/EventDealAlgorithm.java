package com.wntime.event.service;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;

/**
 * 事件处理算法
 */
public interface EventDealAlgorithm {

    R deal(EventReportForm eventReportForm);

}
