package com.wntime.event.service.algorithm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.R;
import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.modules.officer.entity.DriverEntity;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.wntime.modules.officer.service.DriverService;
import com.wntime.modules.officer.service.SafetyOfficerService;
import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.service.common.service.BusServiceLogService;
import com.wntime.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("vehiclesParkedEvent")
public class VehiclesParkedEvent extends AbstractEventHandle implements EventDealAlgorithm {

    @Autowired
    private DriverService driverService;
    @Autowired
    private SafetyOfficerService safetyOfficerService;
    @Autowired
    private ItineraryReceiptService itineraryReceiptService;
    @Autowired
    private BusServiceLogService busServiceLogService;

    /***
     * @Author Buxl
     * @Description 车辆停靠事件处理
     * @Date 14:00 2021/1/23
     * @Param [eventReportForm]
     * @return void
     **/
    @Override
    public R deal(EventReportForm eventReportForm) {
        int eventDetail = eventReportForm.getEventDetail();
        ItineraryReceiptEntity entity = eventReportForm.getItineraryReceipt();
        Long currentBusStationId = null;
        if(eventReportForm.getTags().get("station_id") != null){
            currentBusStationId = Long.parseLong((String) eventReportForm.getTags().get("station_id"));
        }

        if(eventDetail == 1){
            //人员上车
            getOnBus(eventReportForm,entity);
        }
        if(eventDetail == 5){
            //车辆到站
            arrive(eventReportForm,currentBusStationId,entity);
        }
        if(eventDetail == 6){
            //车辆离站
            leave(eventReportForm,currentBusStationId,entity);
        }
        return R.ok();
    }
    
    /**
     * @Author Buxl
     * @Description 处理人员上车的逻辑
     * @Date 8:53 2021/1/28
     * @Param [currentBusStationId, entity]
     * @return void
     **/
    private void getOnBus(EventReportForm eventReportForm,ItineraryReceiptEntity entity){
        //人员上车
        //1、检查是否为司机
        String idNo = (String) eventReportForm.getTags().get("id_no");
        DriverEntity driverEntity = driverService.getDriverByIdNo(idNo);
        if(driverEntity != null){
            entity.setDriverId(driverEntity.getId());
            entity.setDriverFullName(driverEntity.getDriverName());
            entity.setDriverPosition("1".equals(driverEntity.getIsPrimary()) ? "主岗" : "备岗");
            entity.setDriverBodyTemperature(Double.valueOf(String.valueOf(eventReportForm.getTags().get("temperature"))));
            entity.setDriverWearMask("1".equals(String.valueOf(eventReportForm.getTags().get("is_face_mask"))) ? "已佩戴" : "未佩戴");
            entity.setDriverHandDisinfection("1".equals(String.valueOf(eventReportForm.getTags().get("is_hand_disinfect"))) ? "已消毒" : "未消毒");
            entity.setBusStatus("1");
            entity.setItineraryStartTime(DateUtils.getTimestamp());
            entity.setModifyDt(DateUtils.getTimestamp());
            itineraryReceiptService.updateById(entity);
        }else{
            //2、检查是否为安全员
            SafetyOfficerEntity safetyOfficer = safetyOfficerService.getSafetyOfficerByIdNo(idNo);
            if(safetyOfficer != null){
                entity.setSafetyOfficerId(safetyOfficer.getId());
                entity.setSafetyOfficerFullName(safetyOfficer.getSafetyOfficerName());
                entity.setSafetyOfficerPosition("1".equals(safetyOfficer.getIsPrimary()) ? "主岗" : "备岗");
                entity.setSafetyOfficerBodyTemperature(Double.valueOf(String.valueOf(eventReportForm.getTags().get("temperature"))));
                entity.setSafetyOfficerWearMask("1".equals(String.valueOf(eventReportForm.getTags().get("is_face_mask"))) ? "已佩戴" : "未佩戴");
                entity.setSafetyOfficerHandDisinfectio("1".equals(String.valueOf(eventReportForm.getTags().get("is_hand_disinfect"))) ? "已消毒" : "未消毒");
                entity.setModifyDt(DateUtils.getTimestamp());
                itineraryReceiptService.updateById(entity);
            }
        }

    }
    
    
    /**
     * @Author Buxl
     * @Description 处理车辆到站的逻辑
     * @Date 8:50 2021/1/28
     * @Param [currentBusStationId, entity]
     * @return void
     **/
    private void arrive(EventReportForm eventReportForm,Long currentBusStationId,ItineraryReceiptEntity entity){
        //车辆到站
        if(currentBusStationId != null){
            //1、查询行程车站列表(这里是倒序排的)
            List<ItineraryReceiptEntity> list = itineraryReceiptService.getStationByItinerary(entity.getItineraryId());

            //2、判断是否为第一站,车辆到达第一站,行程开始；这里从第二站开始算，第一站为司机和安全员汇合点，不算在行程内
            int firstStationIndex = list.size() - 1;
            if(Constant.LineDirection.UP.getValue().equals(entity.getItineraryDirection())){
                //上行从第二站开始，第一站为汇合点
                firstStationIndex = list.size() - 2;
            }
            if(currentBusStationId.longValue() == list.get(firstStationIndex).getBusStationId().longValue()){
                //行程开始
                entity.setItineraryStatus("2");
                entity.setModifyDt(DateUtils.getTimestamp());
                itineraryReceiptService.updateById(entity);
            }

            //3、记录当前到站信息：当前到站车站id、到站时间、下一站车站id
            for(int i=0;i<list.size();i++){
                ItineraryReceiptEntity ire = list.get(i);
                if(currentBusStationId.longValue() == ire.getBusStationId().longValue()){
                    Long nextStationId = null;
                    if(i == 0){
                        //最后一站
                        nextStationId = currentBusStationId;
                    }else{
                        //下一站id，这里是倒排序，所以去上一条数据
                        nextStationId = list.get(i -1).getBusStationId();
                    }
                    LogBusServiceEntity logBusServiceEntity = busServiceLogService.getOne(new QueryWrapper<LogBusServiceEntity>()
                            .eq("itinerary_id",entity.getItineraryId())
                            .eq("bus_station_id",currentBusStationId));
//                            .and(wrapper ->{
//                                wrapper.eq("next_station_id",currentBusStationId)
//                                        .or()
//                                        .eq("bus_station_id",currentBusStationId);
//                            }));
                    if(logBusServiceEntity != null){
                        logBusServiceEntity.setBusStationId(currentBusStationId);
                        logBusServiceEntity.setNextStationId(nextStationId);
                        logBusServiceEntity.setArrivalTime(DateUtil.date2String(new Date(eventReportForm.getEventDate()),DateUtil.HH_mm_ss));
                        logBusServiceEntity.setModifiedDate(DateUtils.getTimestamp());
                        busServiceLogService.updateById(logBusServiceEntity);
                    }else{
                        logBusServiceEntity = new LogBusServiceEntity();
                        logBusServiceEntity.setItineraryId(entity.getItineraryId());
                        logBusServiceEntity.setBusStationId(currentBusStationId);
                        logBusServiceEntity.setNextStationId(nextStationId);
                        logBusServiceEntity.setArrivalTime(DateUtil.date2String(new Date(eventReportForm.getEventDate()),DateUtil.HH_mm_ss));
                        logBusServiceEntity.setCreatedDate(DateUtils.getTimestamp());
                        busServiceLogService.save(logBusServiceEntity);
                    }
                    break;
                }
            }
        }
    }
    
    /**
     * @Author Buxl
     * @Description 处理车辆离站的逻辑
     * @Date 8:51 2021/1/28
     * @Param [currentBusStationId, entity]
     * @return void
     **/
    private void leave(EventReportForm eventReportForm,Long currentBusStationId,ItineraryReceiptEntity entity){
        //车辆离站
        if(currentBusStationId != null){
            //终点站
            List<ItineraryReceiptEntity> list = itineraryReceiptService.getStationByItinerary(entity.getItineraryId());
            if(currentBusStationId.longValue() == list.get(0).getBusStationId().longValue()){

                //1、是终点、车辆离站,行程结束。这里行程状态先不改变，只用记录下行程结束时间，
                entity.setItineraryEndTime(DateUtils.getTimestamp());
                entity.setModifyDt(DateUtils.getTimestamp());
                itineraryReceiptService.updateById(entity);

                //2、统计行程回单的数据，并将行程状态设置为结束 异步调用
                itineraryReceiptService.statReceiptData(entity.getItineraryId());
            }else{
                //修改车辆离站时间
                LogBusServiceEntity logBusServiceEntity = busServiceLogService.getOne(new QueryWrapper<LogBusServiceEntity>()
                        .eq("itinerary_id",entity.getItineraryId())
                        .eq("bus_station_id",currentBusStationId));
                if(logBusServiceEntity != null){
                    logBusServiceEntity.setLeaveTime(DateUtil.date2String(new Date(eventReportForm.getEventDate()),DateUtil.HH_mm_ss));
                    logBusServiceEntity.setModifiedDate(DateUtils.getTimestamp());
                    busServiceLogService.updateById(logBusServiceEntity);
                }
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(DateUtil.date2String(new Date(1612171339000L),DateUtil.yyyy_MM_dd_HH_mm_ss));
    }
}
