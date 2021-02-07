package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusMonitorSummary;
import com.wntime.event.repo.BusMonitorSummaryRepository;
import com.wntime.event.service.EventDealAlgorithm;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.service.BusServiceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * 车站人流量事件
 *
 * @author 79448
 * @date 2020/8/25 17:00
 */
@Component
public class StationFlowEvent extends AbstractEventHandle implements EventDealAlgorithm {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private BusMonitorSummaryRepository busMonitorSummaryRepository;
    @Resource
    private BusServiceLogService busServiceLogService;
    @Override
    public R deal(EventReportForm eventReportForm) {
        if(StringUtils.isEmpty(eventReportForm.getEventTargetCode())){
            logger.error("到站信息有误：{}",eventReportForm);
            return R.error("到站信息有误：" + eventReportForm.toString());
        }
        Map<String, Object> tags = eventReportForm.getTags();
        Object busStationId = tags.get("bus_station");
        Object busLine = tags.get("bus_line");
        LineStationInfoVo station = handleGPSConvert(busStationId,busLine, eventReportForm.getEventDate(), eventReportForm.getEventDetailId());
        if(station!=null){
            Object latitude = tags.get("latitude");
            if (latitude != null && !latitude.equals("")) {
                station.setBusStationLatitude(Double.parseDouble(String.valueOf(latitude)));
            }
            Object longitude = tags.get("longitude");
            if (longitude != null && !longitude.equals("")) {
                station.setBusStationLongitude(Double.parseDouble(String.valueOf(longitude)));
            }
        }
        BusDateKey key = new BusDateKey( eventReportForm.getBusId());
        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(key);

        switch (eventReportForm.getEventTargetCode()){
            case "arrive_at"://到站
                if(station!=null){
                    optional.ifPresent(busMonitorSummary -> {
                        if(busMonitorSummary.getBusStationId() !=null && busMonitorSummary.getBusStationId()!=station.getBusStationId()){//如果缓存中的站点和当前上传计算出的站点不一致，则表示到达了下一站
                            //禁用公交车最近一条有效营运日志数据
                            busServiceLogService.disableLatestBusServiceLog(eventReportForm.getBusId(),station.getCompanyLineId(),busMonitorSummary.getBusStationId(),station.getBusStationId());
                        }
                        //创建本站的营运数据日志
                        busServiceLogService.createBusServiceLog(eventReportForm.getBusId(),station);
                        busMonitorSummary.setStationBoardNum(0);
                        busMonitorSummary.setStationDownNum(0);
                        busMonitorSummary.setBusStationName(station.getBusStationName());
                        busMonitorSummary.setBusStationId(station.getBusStationId());
                        busMonitorSummaryRepository.save(busMonitorSummary);
                    });
                }else {
                    optional.ifPresent(busMonitorSummary -> {
                        busMonitorSummary.setStationBoardNum(0);
                        busMonitorSummary.setStationDownNum(0);
                        busMonitorSummaryRepository.save(busMonitorSummary);
                    });
                }
                //创建本站的营运日志
                break;
            case "off_site"://离站
                //将本站的营运日志设置为不可用
                optional.ifPresent(busMonitorSummary ->{
                        if(station!=null) {
                            busServiceLogService.disableLatestBusServiceLog(eventReportForm.getBusId(), station.getCompanyLineId(), busMonitorSummary.getBusStationId(), station.getBusStationId());
                        }
                });
                break;
                default:break;
        }
        return R.ok();
    }
}
