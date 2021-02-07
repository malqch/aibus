package com.wntime.event.controller;

import com.wntime.common.utils.Constant;
import com.wntime.common.utils.R;
import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.event.entity.*;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.*;
import com.wntime.event.service.algorithm.EventDealAlgorithmContext;
import com.wntime.service.common.service.BusDriveLogService;
import com.wntime.service.common.service.CompanyLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "事件上报controller",tags = "事件上报接口")
@RestController
@RequestMapping("/event")
public class EventReportController {

    @Resource
    private InfoEventTypeService infoEventTypeService;
    @Resource
    private InfoEventTargetService infoEventTargetService;
    @Resource
    private InfoEventLevelService infoEventLevelService;
    @Resource
    private InfoCollectEventService infoCollectEventService;
    @Resource
    private LogEventDetailService logEventDetailService;
    @Resource
    private BusDriveLogService busDriveLogService;
    @Resource
    private CompanyLineService companyLineService;
    @Resource
    private EventDealAlgorithmContext eventDealAlgorithmContext;
    @Resource
    private ItineraryReceiptService itineraryReceiptService;

    @ApiOperation(value = "事件上报",notes = "上报公交车上的事件，包括摄像头和IOT设备采集到的数据")
    @PostMapping("/report")
    public R report(@RequestBody @Validated EventReportForm eventReportForm){
        //数据校验；
        InfoEventTypeEntity infoEventType = infoEventTypeService.getById(eventReportForm.getEventTypeId());
        if(infoEventType==null){
            return R.error("事件类型不存在");
        }
        eventReportForm.setEventTypeName(infoEventType.getEventTypeName());
        eventReportForm.setEventTypeCode(infoEventType.getEventTypeCode());
        InfoEventTargetEntity eventTarget = infoEventTargetService.getById(eventReportForm.getEventTargetId());
        if(eventTarget==null){
            return R.error("事件标签不存在");
        }
        eventReportForm.setEventTargetName(eventTarget.getEventTargetName());
        eventReportForm.setEventTargetCode(eventTarget.getEventTargetCode());
        InfoEventLevelEntity eventLevel = infoEventLevelService.getById(eventReportForm.getEventLevelId());
        if(eventLevel==null){
            return R.error("事件等级不存在");
        }
        eventReportForm.setEventLevelName(eventLevel.getEventLevelName());
        InfoCollectEventEntity collectEvent = infoCollectEventService.getById(eventReportForm.getCollectEventId());
        if(collectEvent==null){
            return R.error("事件采集不存在");
        }
        if(!collectEvent.getDeviceTypeId().equals(eventReportForm.getDeviceTypeId())){
            return R.error("该设备类型上报数据不匹配");
        }
        eventReportForm.setEventDetail(collectEvent.getEventDetail());
        //数据落库
//        Timestamp eventDate = new Timestamp(eventReportForm.getEventDate());
        //保存公交车辆行驶数据信息
//        LogBusDriveEntity logBusDriveEntity=new LogBusDriveEntity();
//        CarStatusInfoForm carInfo = eventReportForm.getCarStatusInfo();
//        BeanUtils.copyProperties(carInfo,logBusDriveEntity);
//        logBusDriveEntity.setBusLatitude(carInfo.getFactoryLatitude());
//        logBusDriveEntity.setBusLongitude(carInfo.getFactoryLongitude());
//        logBusDriveEntity.setBusId(eventReportForm.getBusId());
//        logBusDriveEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
//        logBusDriveEntity.setCreatedDate(eventDate);
//        logBusDriveEntity.setBusStatus((long)carInfo.getBusStatus());
//        Integer motorStatus=carInfo.getBusMotorStatus();
//        if(motorStatus==3){
//            motorStatus=0;
//        }else {
//            motorStatus=1;
//        }
//        logBusDriveEntity.setBusMotorStatus((long)motorStatus);
//        Integer batteryStatus=carInfo.getBusBatteryStatus();
//        if(batteryStatus==3){
//            batteryStatus=0;
//        }else {
//            batteryStatus=1;
//        }
//        logBusDriveEntity.setBusBatteryStatus((long)batteryStatus);
//        busDriveLogService.insertBusDriveLog(logBusDriveEntity);

//        busDriveLogService.busDriveStatus(eventReportForm.getBusId(),carInfo.getBusStatus());
        //保存事件日志
//        LogEventDetailEntity logEventDetailEntity = new LogEventDetailEntity();
//        BeanUtils.copyProperties(eventReportForm,logEventDetailEntity);
//        logEventDetailEntity.setBusDriveId(logBusDriveEntity.getBusDriveId());
//        logEventDetailEntity.setIsEnabled(Constant.Enabled.ENABLE.getValue());
//        logEventDetailEntity.setCreatedDate(eventDate);
//
//        Map<String,Object> params = new HashMap<>();
//        params.put("busId",eventReportForm.getBusId());
//        params.put("itineraryStatus", Arrays.asList("1","2"));
//        List<ItineraryReceiptEntity> itinerarys = itineraryReceiptService.getItineraryByCon(params);
//        if(itinerarys != null && itinerarys.size() >= 1){
//            logEventDetailEntity.setItineraryId(itinerarys.get(0).getItineraryId());
//            logEventDetailService.save(logEventDetailEntity);
//            eventReportForm.setEventDetailId(logEventDetailEntity.getEventDetailId());
//
//            eventDealAlgorithmContext.dealByEventCode(collectEvent.getEventType(),eventReportForm);
//        }

        R r = eventDealAlgorithmContext.dealByEventCode(collectEvent.getEventType(),eventReportForm);

        return r;
    }

    @ApiOperation(value = "清除事件统计缓存",notes = "清除事件统计缓存")
    @GetMapping("/clear")
    public R clear(){
        eventDealAlgorithmContext.clear();
        return R.ok();
    }
}
