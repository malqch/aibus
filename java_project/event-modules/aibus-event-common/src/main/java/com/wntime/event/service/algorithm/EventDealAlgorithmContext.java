package com.wntime.event.service.algorithm;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.R;
import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.event.entity.LogEventDetailEntity;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusLineFullSeatRate;
import com.wntime.event.region.BusLineFullSeatRateKey;
import com.wntime.event.repo.*;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.event.service.EventHandleService;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 79448
 * @date 2020/8/25 17:21
 */
@Component
public class EventDealAlgorithmContext{

    @Resource
    private EventHandleService eventHandleService;
    @Resource
    private BusLineFullSeatRateRepository busLineFullSeatRateRepository;
    @Resource
    private BusMonitorSummaryRepository busMonitorSummaryRepository;
    @Resource
    private DriverViolationRepository driverViolationRepository;
    @Resource
    private BusAlarmInfoRepository busAlarmInfoRepository;
    @Resource
    private DriverViolationStatisticsRepository driverViolationStatisticsRepository;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;
    @Resource
    private ItineraryReceiptService itineraryReceiptService;
    @Resource
    private LogEventDetailService logEventDetailService;

    private final Map<String, EventDealAlgorithm> algorithmMap = new ConcurrentHashMap<>();


    public EventDealAlgorithmContext(Map<String, EventDealAlgorithm> map) {
        this.algorithmMap.clear();
        map.forEach((k, v) -> {
            this.algorithmMap.put(k, v);
        });
    }

//    @Async
    @Transactional(rollbackFor = Exception.class)
    public R dealByEventCode(int eventTypeCode, EventReportForm eventReportForm) {

        R returnData = R.ok();

        Map<String,Object> params = new HashMap<>();
        params.put("busId",eventReportForm.getBusId());
        params.put("itineraryStatus", Arrays.asList("1","2"));
        List<ItineraryReceiptEntity> itinerarys = itineraryReceiptService.getItineraryByCon(params);
        if(itinerarys != null && itinerarys.size() >= 1){
            Long eventDetailId = IdWorker.getId();
            eventReportForm.setEventDetailId(eventDetailId);
            eventReportForm.setItineraryReceipt(itinerarys.get(0));

            //每一类事件的逻辑处理
            String name = "";
            switch (eventTypeCode) {
                case 2: //车辆停靠
                    name = "vehiclesParkedEvent";
                    break;
                case 3: //学生违纪
                    name = "studentDisciplinaryEvent";
                    break;
                case 11: //行驶情况
                    name = "drivingConditionsEvent";
                    break;
            }

            if (name != "") {
                returnData = this.algorithmMap.get(name).deal(eventReportForm);
            }

            if(returnData.getCode() == 0){
                //保存事件日志
                Timestamp eventDate = new Timestamp(eventReportForm.getEventDate());
                LogEventDetailEntity logEventDetailEntity = new LogEventDetailEntity();
                BeanUtils.copyProperties(eventReportForm,logEventDetailEntity);
                logEventDetailEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
                logEventDetailEntity.setCreatedDate(eventDate);
                logEventDetailEntity.setItineraryId(itinerarys.get(0).getItineraryId());
                logEventDetailEntity.setEventDetailId(eventDetailId);
                logEventDetailService.save(logEventDetailEntity);

                //保存事件标签
                eventHandleService.saveTags(eventReportForm);
            }
        }
        return returnData;
    }

    public void clear(){
        busAlarmInfoRepository.deleteAll(busAlarmInfoRepository.findAll());
        busLineFullSeatRateRepository.deleteAll(busLineFullSeatRateRepository.findAll());
        busMonitorSummaryRepository.deleteAll(busMonitorSummaryRepository.findAll());
        driverViolationRepository.deleteAll(driverViolationRepository.findAll());
        driverViolationStatisticsRepository.deleteAll(driverViolationStatisticsRepository.findAll());
        busRealtimeMonitorStatisticsRepository.deleteAll(busRealtimeMonitorStatisticsRepository.findAll());
    }
 }
