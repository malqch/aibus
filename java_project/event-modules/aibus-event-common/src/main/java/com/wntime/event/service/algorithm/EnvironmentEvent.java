package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 环境相关事件（温度、湿度、PM2.5、PM10、二氧化碳）
 * @author 79448
 * @date 2020/8/25 17:02
 */
@Component
public class EnvironmentEvent extends AbstractEventHandle implements EventDealAlgorithm {


    @Override
    public R deal(EventReportForm eventReportForm) {
        Map<String, Object> tags = eventReportForm.getTags();
        Object busStationId = tags.get("bus_station");
        Object busLine = tags.get("bus_line");
        handleGPSConvert(busStationId,busLine,eventReportForm.getEventDate(),eventReportForm.getEventDetailId());
        setBusAlarmStatus(eventReportForm.getBusId());

        return R.ok();
    }
}