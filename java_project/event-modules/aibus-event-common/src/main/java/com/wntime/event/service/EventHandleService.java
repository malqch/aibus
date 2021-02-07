package com.wntime.event.service;

import com.wntime.event.form.EventReportForm;

public interface EventHandleService {

    void saveTags(EventReportForm eventReportForm);

    void generalStatistics(long busId);

}
