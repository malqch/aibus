package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 冒用卡片事件
 * @author 79448
 * @date 2020/8/25 16:57
 */
@Component
public class FakeCardEvent extends AbstractEventHandle implements EventDealAlgorithm {


    @Override
    public R deal(EventReportForm eventReportForm) {
        Map<String, Object> tags = eventReportForm.getTags();
        Object busStationId = tags.get("bus_station");
        Object busLine = tags.get("bus_line");
        handleGPSConvert(busStationId,busLine,eventReportForm.getEventDate(),eventReportForm.getEventDetailId());

        return R.ok();
    }
}
