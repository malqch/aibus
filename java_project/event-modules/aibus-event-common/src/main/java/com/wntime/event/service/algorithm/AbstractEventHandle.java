package com.wntime.event.service.algorithm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.event.dao.InfoEventDescriptionDao;
import com.wntime.event.entity.InfoEventTargetEntity;
import com.wntime.event.entity.LogEventAttachEntity;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.BusAlarmInfo;
import com.wntime.event.region.BusMonitorSummary;
import com.wntime.event.repo.BusAlarmInfoRepository;
import com.wntime.event.repo.BusMonitorSummaryRepository;
import com.wntime.event.service.InfoEventTargetService;
import com.wntime.event.service.LogEventAttachService;
import com.wntime.event.vo.AlarmInfoItem;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.BusStationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 79448
 * @date 2020/9/1 11:18
 */
public abstract class AbstractEventHandle {


    private static String regx="\\{(.*?)\\}";
    public final static Pattern pattern = Pattern.compile(regx);
    @Resource
    private BusStationService busStationService;
    @Resource
    private InfoEventTargetService infoEventTargetService;
    @Resource
    private LogEventAttachService logEventAttachService;
    @Resource
    private BusAlarmInfoRepository busAlarmInfoRepository;
    @Resource
    private InfoEventDescriptionDao infoEventDescriptionDao;
    @Resource
    private BusInfoService busInfoService;


    @Transactional(rollbackFor = Exception.class)
    public LineStationInfoVo handleGPSConvert(Object busStationId,Object companyLineId, long eventDate, Long eventDetailId){
        LineStationInfoVo vo=null;
        if ( busStationId != null && !busStationId.equals("") && companyLineId!=null && !companyLineId.equals("") ) {
            LogEventAttachEntity logEventAttachEntity = new LogEventAttachEntity();
            logEventAttachEntity.setCreatedDate(new Timestamp(eventDate));
            logEventAttachEntity.setEventDetailId(eventDetailId);
            InfoEventTargetEntity target = infoEventTargetService.getOne(new QueryWrapper<InfoEventTargetEntity>().eq("event_target_code", "bus_station"));
            logEventAttachEntity.setEventTargetId(target.getEventTargetId());
            logEventAttachEntity.setCollectAttachLink(Long.valueOf(String.valueOf(busStationId)));
            logEventAttachService.save(logEventAttachEntity);
            vo = busStationService.queryBusStationInfo(Long.valueOf(String.valueOf(busStationId)), Long.valueOf(String.valueOf(companyLineId)));
        }
        return vo;
    }

    /**
     * 处理ai识别告警
     * @param eventReportForm
     */
    protected BusAlarmInfo assembleBusAlarmInfo(EventReportForm eventReportForm){
        BusDateKey key = new BusDateKey(eventReportForm.getBusId());
        Optional<BusAlarmInfo> optional = busAlarmInfoRepository.findById(key);
        BusAlarmInfo busAlarmInfo = optional.orElse(new BusAlarmInfo(key));
        Set<AlarmInfoItem> alarms = busAlarmInfo.getAlarms();
        AlarmInfoItem item=new AlarmInfoItem();
        item.setEventTypeId(eventReportForm.getEventTypeId());
        item.setType(eventReportForm.getEventTargetName());
        item.setEventTypeCode(eventReportForm.getEventTypeCode());
        String description = infoEventDescriptionDao.queryDescriptionByTargetId(eventReportForm.getEventTargetId());
        Map<String, Object> tags = eventReportForm.getTags();
        if(!StringUtils.isEmpty(description)){
            Matcher m = pattern.matcher(description);
            StringBuffer sb=new StringBuffer();
            while (m.find()){
                m.appendReplacement(sb,String.valueOf(tags.get(m.group(1))));
            }
            m.appendTail(sb);
            item.setDesc(sb.toString());
        }
        item.setLevel(eventReportForm.getEventLevelName());
        item.setEventDate(new Date(eventReportForm.getEventDate()));

        Set<Map.Entry<String, Object>> entrySet = tags.entrySet();
        Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> next = iter.next();
            Object value = next.getValue();
            if(next.getKey().indexOf("img")!=-1){
                item.addImage((String)value);
            }
        }
        alarms.add(item);
        return busAlarmInfo;
    }

    public void setBusAlarmStatus(long busId){
        busInfoService.updateBusInfoStatus(busId,1);
    }
}
