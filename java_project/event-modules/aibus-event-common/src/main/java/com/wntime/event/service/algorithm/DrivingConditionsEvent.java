package com.wntime.event.service.algorithm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.R;
import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.customer.service.ItineraryReceiptService;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.service.common.entity.LogBusDriveEntity;
import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.service.common.service.BusDriveLogService;
import com.wntime.service.common.service.BusServiceLogService;
import com.wntime.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Component("drivingConditionsEvent")
public class DrivingConditionsEvent extends AbstractEventHandle implements EventDealAlgorithm {

    @Autowired
    private BusDriveLogService busDriveLogService;
    @Resource
    private ItineraryReceiptService itineraryReceiptService;
    @Autowired
    private BusServiceLogService busServiceLogService;

    /**
     * 行驶情况
     * @param eventReportForm
     */
    @Override
    public R deal(EventReportForm eventReportForm) {
        int eventDetail = eventReportForm.getEventDetail();
        Long currentBusStationId = null;
        if(eventReportForm.getTags().get("station_id") != null){
            currentBusStationId = Long.parseLong((String) eventReportForm.getTags().get("station_id"));
        }

        R returnData = R.ok();

        if(eventDetail == 1){
            //时速
            ItineraryReceiptEntity itineraryReceipt = eventReportForm.getItineraryReceipt();
            if("2".equals(itineraryReceipt.getItineraryStatus())){
                /**
                 * 行程状态为 行驶中，记录时速
                 * 1、检测同一个行程id、同一地点是否有记录，如果有就不做处理了，说明刚才处理过了
                 */
                Double longitude = Double.valueOf(String.valueOf(eventReportForm.getTags().get("longitude")));
                Double latitude = Double.valueOf(String.valueOf(eventReportForm.getTags().get("latitude")));
                LogBusDriveEntity logBusDriveEntity = busDriveLogService.getOne(
                        new QueryWrapper<LogBusDriveEntity>()
                                .eq("itinerary_id", itineraryReceipt.getItineraryId())
                                .eq("bus_longitude", longitude)
                                .eq("bus_latitude", latitude));
                if(logBusDriveEntity == null){
                    Double speed = Double.valueOf(String.valueOf(eventReportForm.getTags().get("speed")));
                    logBusDriveEntity = new LogBusDriveEntity();
                    logBusDriveEntity.setItineraryId(itineraryReceipt.getItineraryId());
                    logBusDriveEntity.setBusSpeed(speed);
                    logBusDriveEntity.setBusLongitude(longitude);
                    logBusDriveEntity.setBusLatitude(latitude);
                    logBusDriveEntity.setIsEnabled(1);
                    logBusDriveEntity.setCreatedDate(new Timestamp(eventReportForm.getEventDate()));
                    busDriveLogService.save(logBusDriveEntity);

                    //计算到下一站的时间
//                    calcuNextStationArriveM(itineraryReceipt.getItineraryId(),speed,longitude,latitude);
                }else{
                    returnData = R.error("该时刻的速度已记录过了,不用处理...");
                }
            }else{
                returnData = R.error("行程没有开始，不用处理...");
            }

            //返回bus_station_id,是否为终点站,行程方向、是否为起点站
            if(currentBusStationId != null){
                returnData.getData().put("direction",Integer.valueOf(itineraryReceipt.getItineraryDirection()).intValue());
                List<ItineraryReceiptEntity> list = itineraryReceiptService.getStationByItinerary(itineraryReceipt.getItineraryId());
                if(currentBusStationId.longValue() == list.get(0).getBusStationId().longValue()){
                    //是终点站
                    returnData.getData().put("id_terminal",2);
                }else{
                    //不是终点站
                    returnData.getData().put("id_terminal",1);
                }

                if(currentBusStationId.longValue() == list.get(list.size() - 1).getBusStationId().longValue()){
                    //是首发站
                    returnData.getData().put("is_first_station",2);
                }else{
                    //不是首发站
                    returnData.getData().put("is_first_station",1);
                }

            }

        }

        return returnData;
    }

    /**
     * @Author Buxl
     * @Description 计算下一站到达时间
     * @Date 8:57 2021/1/28
     * @Param []
     * @return void
     **/
    private void calcuNextStationArriveM(Long itineraryId, Double speed,Double longitude,Double latitude){
        if(speed > 0){
            //获取下一站的位置
            ItineraryReceiptEntity stationGPS = itineraryReceiptService.getNextStationGPS(itineraryId);
            long nextStationId = stationGPS.getBusStationId() == null
                    ? stationGPS.getFirstStationId() : stationGPS.getBusStationId();
            double nextLongitude = stationGPS.getBusStationLongitude() == null
                    ? stationGPS.getFirstStationLongitude() : stationGPS.getBusStationLongitude();
            double nextLatitude = stationGPS.getBusStationLatitude() == null
                    ? stationGPS.getFirstStationLatitude() : stationGPS.getBusStationLatitude();
            double distance = DistanceUtil.getShortestDistance(longitude,latitude,nextLongitude,nextLatitude);

            LogBusServiceEntity logBusServiceEntity = busServiceLogService.getOne(new QueryWrapper<LogBusServiceEntity>()
                    .eq("itinerary_id",itineraryId)
                    .eq("next_station_id",nextStationId));
            //速度转为 m/min
            BigDecimal speed_ = BigDecimal.valueOf(speed).multiply(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(60),2,BigDecimal.ROUND_HALF_UP);
            int time = BigDecimal.valueOf(distance).divide(speed_,0,BigDecimal.ROUND_HALF_UP).intValue();
            if(logBusServiceEntity != null){
                logBusServiceEntity.setNextStationArriveMin(time);
                logBusServiceEntity.setModifiedDate(DateUtils.getTimestamp());
                busServiceLogService.updateById(logBusServiceEntity);
            }else{
                logBusServiceEntity = new LogBusServiceEntity();
                logBusServiceEntity.setItineraryId(itineraryId);
                logBusServiceEntity.setNextStationArriveMin(time);
                logBusServiceEntity.setModifiedDate(DateUtils.getTimestamp());
                busServiceLogService.save(logBusServiceEntity);
            }
        }

    }

}
