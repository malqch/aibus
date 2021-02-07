package com.wntime.event.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.FileUtil;
import com.wntime.event.config.EventFileConfig;
import com.wntime.event.dao.LogEventDetailDao;
import com.wntime.event.entity.InfoEventTargetEntity;
import com.wntime.event.entity.LogEventAttachEntity;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.region.*;
import com.wntime.event.repo.*;
import com.wntime.event.serive.EventService;
import com.wntime.event.service.EventHandleService;
import com.wntime.event.service.InfoEventTargetService;
import com.wntime.event.service.LogEventAttachService;
import com.wntime.event.service.algorithm.AbstractEventHandle;
import com.wntime.event.vo.*;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.BusServiceLogService;
import com.wntime.service.common.vo.BusBaseInfoVo;
import com.wntime.service.common.vo.BusPassengerAgeVo;
import com.wntime.service.common.vo.BusPassengerVo;
import com.wntime.service.common.vo.CompanyLinePassengerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;

/**
 * @author 79448
 * @date 2020/8/28 11:06
 */
@Service
public class EventHandleServiceImpl implements EventHandleService, EventService {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private EventFileConfig eventFileConfig;
    @Resource
    private InfoEventTargetService infoEventTargetService;
    @Resource
    private LogEventAttachService logEventAttachService;

    @Resource
    private BusAlarmInfoRepository busAlarmInfoRepository;
    @Resource
    private BusMonitorSummaryRepository busMonitorSummaryRepository;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;
    @Resource
    private DriverViolationStatisticsRepository driverViolationStatisticsRepository;
    @Resource
    private BusLineFullSeatRateRepository busLineFullSeatRateRepository;
    @Resource
    private BusInfoService busInfoService;
    @Resource
    private PassengerSummaryRepository passengerSummaryRepository;

    @Resource
    private BusServiceLogService busServiceLogService;
    @Resource
    private LogEventDetailDao logEventDetailDao;
    /**
     * 保存标签信息
     * @param eventReportForm
     */
    @Override
    public void saveTags(EventReportForm eventReportForm) {
        //保存标签数据
        Map<String, Object> tags = eventReportForm.getTags();
        Set<Map.Entry<String, Object>> entrySet = tags.entrySet();
        Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();
        List<LogEventAttachEntity> attaches = new ArrayList<>();
        while (iter.hasNext()) {
            Map.Entry<String, Object> next = iter.next();
            Object value = next.getValue();
            if (value == null || value == "" ){
                iter.remove();
                continue;
            }
            LogEventAttachEntity logEventAttachEntity = new LogEventAttachEntity();
            logEventAttachEntity.setCreatedDate(new Timestamp(eventReportForm.getEventDate()));
            logEventAttachEntity.setEventDetailId(eventReportForm.getEventDetailId());
            InfoEventTargetEntity target = infoEventTargetService.getOne(new QueryWrapper<InfoEventTargetEntity>().eq("event_target_code", next.getKey()));
            if(target==null){
                continue;
            }
            logEventAttachEntity.setEventTargetId(target.getEventTargetId());
            if (target.getEventTargetGrope().equals(Constant.EventTargetGroup.LINK.getName())) {
                logEventAttachEntity.setCollectAttachLink(Long.valueOf(String.valueOf(value)) );
            } else if (target.getEventTargetGrope().equals(Constant.EventTargetGroup.VALUE.getName())) {
                logEventAttachEntity.setCollectAttachValue(Double.valueOf(String.valueOf(value)));
            } else if (target.getEventTargetGrope().equals(Constant.EventTargetGroup.CHAR.getName())) {
                logEventAttachEntity.setCollectAttachChar((String) value);
            }else if(target.getEventTargetGrope().equals(Constant.EventTargetGroup.IMAGE.getName())){
                //判断如果是文件就要保存到文档
                value = saveFile(IdWorker.get32UUID() + ".png", (String) value, "/" + eventReportForm.getBusId() + "/");
                logEventAttachEntity.setCollectAttachChar((String) value);
            }
            //将属于设置回去 主要是方便其他地方或者转化后的值
            tags.put(next.getKey(),value);
            attaches.add(logEventAttachEntity);
        }
        logEventAttachService.saveBatch(attaches);
    }


    /**
     * 生成基本统计数据
     * @param busId
     */
    @Override
    public void generalStatistics(long busId){
        BusDateKey key = new BusDateKey(busId);
        Optional<DriverViolationStatistics> optional = driverViolationStatisticsRepository.findById(key);
        if(!optional.isPresent()){
            driverViolationStatisticsRepository.save(new Wrapper<>(new DriverViolationStatistics(key),key));
        }
        Optional<BusMonitorSummary> mOptional = busMonitorSummaryRepository.findById(key);
        if(!mOptional.isPresent()){
            busMonitorSummaryRepository.save(new Wrapper<>(new BusMonitorSummary(key),key));
        }

        Optional<BusRealtimeMonitorStatistics> optional1 = busRealtimeMonitorStatisticsRepository.findById(key);
        if(!optional1.isPresent()){
            BusBaseInfoVo busInfo = busInfoService.queryBusInfo(busId);
            BusRealtimeMonitorStatistics summary = new BusRealtimeMonitorStatistics(key);
            summary.setPeopleLoadCount(busInfo.getPeopleLoadCount());
            busRealtimeMonitorStatisticsRepository.save(new Wrapper<>(summary,summary.getKey()));
        }
    }

    @Override
    public Map<String, DriverViolationVo> driverViolationMap(long busId){
        Optional<DriverViolationStatistics> optional = driverViolationStatisticsRepository.findById(new BusDateKey(busId));
        Map<String, DriverViolationVo> result=new LinkedHashMap<>();
        result.put("distracted",new DriverViolationVo("司机走神",0));//走神
        result.put("smoke",new DriverViolationVo("吸烟违规",0));//抽烟
        result.put("fatigue",new DriverViolationVo("疲劳驾驶违规",0));//疲劳驾驶
        result.put("use_phone",new DriverViolationVo("打手机违规",0));//使用手机
        if (optional.isPresent()) {
            Set<DriverViolationEvent> events = optional.get().getEvents();
            events.stream().forEach(event -> {
                DriverViolationVo vo = result.get(event.getEventTargetCode());
                if (vo != null) {
                    vo.incrementCount();
                }
                result.put(event.getEventTargetCode(), vo);
            });
        } else {
            List<EventCountVo> list = logEventDetailDao.queryEventTargetGroupCount(busId, result.keySet());
            if(list!=null & !list.isEmpty()){
                for(EventCountVo vo:list){
                    DriverViolationVo violationVo = result.get(vo.getCode());
                    violationVo.setCount(vo.getCount());
                }
            }
        }
        return result;
    }

    @Override
    public List<PassengerFlowVo> queryPassengerFlowInfo(long busId) {
        List<PassengerFlowVo> list=new ArrayList<>();
        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(new BusDateKey(busId));
        PassengerFlowVo boardVo=new PassengerFlowVo(0);
        boardVo.setName("本站上车人数");
        PassengerFlowVo downVo=new PassengerFlowVo(1);
        downVo.setName("本站下车人数");
        PassengerFlowVo passengerVo=new PassengerFlowVo(2);
        passengerVo.setName("当日累计客流数");
        if(optional.isPresent()){
            BusMonitorSummary busMonitorSummary = optional.get();
            List<Integer> nums = Arrays.asList(busMonitorSummary.getBoardNum(), busMonitorSummary.getDownNum(), busMonitorSummary.getPassengerCount());
            Collections.sort(nums);
            Integer max = nums.get(2);

            boardVo.setMax(max);
            boardVo.setNumber(busMonitorSummary.getStationBoardNum());

            downVo.setMax(max);
            downVo.setNumber(busMonitorSummary.getStationDownNum());

            passengerVo.setMax(max);
            passengerVo.setNumber(busMonitorSummary.getBoardNum());
        }else {
            BusPassengerVo passengerCountVo = busServiceLogService.queryTodayBusPassenger(busId);
            if(passengerCountVo!=null){
                boardVo.setNumber(passengerCountVo.getBusGetOn());
                downVo.setNumber(passengerCountVo.getBusGetOff());
                passengerVo.setNumber(passengerCountVo.getTotal());
            }
        }


        list.add(boardVo);
        list.add(downVo);
        list.add(passengerVo);
        return list;
    }

    @Override
    public BusPassengerStatisticsVo queryPassengerFlowInfoAnalysis(long busId) {
        BusPassengerStatisticsVo vo=new BusPassengerStatisticsVo();
        List<PassengerFlowVo> list=new ArrayList<>();
        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(new BusDateKey(busId));
        PassengerFlowVo adultVo=new PassengerFlowVo(0);
        adultVo.setName("18岁到65岁");
        PassengerFlowVo oldVo=new PassengerFlowVo(1);
        oldVo.setName("65岁以上");
        PassengerFlowVo childVo=new PassengerFlowVo(2);
        childVo.setName("18岁以下");
        if(optional.isPresent()){
            BusMonitorSummary busMonitorSummary = optional.get();
            List<Integer> nums = Arrays.asList(busMonitorSummary.getBoardNum(), busMonitorSummary.getDownNum(), busMonitorSummary.getPassengerCount());
            Collections.sort(nums);
            Integer max = nums.get(2);

            adultVo.setMax(max);
            adultVo.setNumber(busMonitorSummary.getAdultCount());

            oldVo.setMax(max);
            oldVo.setNumber(busMonitorSummary.getOldCount());

            childVo.setMax(max);
            childVo.setNumber(busMonitorSummary.getChildrenCount());

            vo.setMaleNum(busMonitorSummary.getMaleCount());
            vo.setFemaleNum(busMonitorSummary.getFemaleCount());
        }else {
            BusPassengerAgeVo ageVo = busServiceLogService.queryTodayBusPassengerAge(busId);
            if(ageVo!=null){
                childVo.setNumber(ageVo.getChildrenTotal());
                childVo.setMax(ageVo.getMax());

                adultVo.setNumber(ageVo.getAdultTotal());
                adultVo.setMax(ageVo.getMax());

                oldVo.setNumber(ageVo.getOldTotal());
                oldVo.setMax(ageVo.getMax());

                vo.setMaleNum(ageVo.getMaleTotal());
                vo.setFemaleNum(ageVo.getFemaleTotal());
            }

        }

        list.add(adultVo);
        list.add(oldVo);
        list.add(childVo);
        vo.setAgeAnalysis(list);
        return vo;
    }

    @Override
    public List<HarmfulAlarmVo> queryHarmfulAlarmInfo(long busId) {
        List<HarmfulAlarmVo> list=new ArrayList<>();
        HarmfulAlarmVo gasVo=new HarmfulAlarmVo();
        gasVo.setAlarmType(0);
        gasVo.setName("可燃物");

        HarmfulAlarmVo temperatureVo=new HarmfulAlarmVo();
        temperatureVo.setAlarmType(1);
        temperatureVo.setName("体温异常");

        Optional<BusMonitorSummary> optional = busMonitorSummaryRepository.findById(new BusDateKey(busId));
        if(optional.isPresent()){
            BusMonitorSummary busMonitorSummary = optional.get();
            gasVo.setNumber(busMonitorSummary.getCombustibleGasAlarmCount());
            temperatureVo.setNumber(busMonitorSummary.getBodyTemperatureAbnormalCount());
        }else {
            Set<String> types = new HashSet<>();
            types.add("combustible_gas");
            types.add("health_epidemic_prevention");
            List<EventCountVo> eventCountList = logEventDetailDao.queryEventTypeGroupCount(busId, types);
            if(eventCountList !=null && !eventCountList.isEmpty()){
                for(EventCountVo vo: eventCountList){
                    if("combustible_gas".equals(vo.getCode())){
                        gasVo.setNumber(vo.getCount());
                    }else if("health_epidemic_prevention".equals(vo.getCode())){
                        temperatureVo.setNumber(vo.getCount());
                    }
                }
            }
        }
        int max=gasVo.getNumber()>temperatureVo.getNumber()?gasVo.getNumber():temperatureVo.getNumber();
        gasVo.setMax(max);
        temperatureVo.setMax(max);
        list.add(gasVo);
        list.add(temperatureVo);
        return list;
    }

    @Override
    public LineFullSeatRateRankVo queryCompanyLineFullSeatRateRank(long companyId,int size) {
        List<BusLineFullSeatRate> list = busLineFullSeatRateRepository.queryBusLineFullSeatRateRankByCompanyId(companyId, LocalDate.now());
        LineFullSeatRateRankVo vo=new LineFullSeatRateRankVo();
        AtomicReference<Double> max=new AtomicReference<>(100.0);

        if(list !=null && !list.isEmpty()){
            list.stream().forEach(busLineFullSeatRate -> {
                setData(vo, busLineFullSeatRate.getCompanyLineCode(),busLineFullSeatRate.getFullSeatRate());
                if(busLineFullSeatRate.getFullSeatRate()> max.get()){
                    max.set(busLineFullSeatRate.getFullSeatRate()*100);
                }
            });
        }else {
            List<CompanyLinePassengerVo> companyLinePasgList = busServiceLogService.queryTodayBusServiceLog(companyId);
            if(companyLinePasgList!=null && !companyLinePasgList.isEmpty()){
                companyLinePasgList.stream().forEach(companyLinePassengerVo -> {

                    double rate = companyLinePassengerVo.calculateFullRate();
                    setData(vo,companyLinePassengerVo.getCompanyLineName(),rate);
                    if(rate> max.get()){
                        max.set(rate *100);
                    }
                });
            }
        }
        vo.setLimit(size);
        vo.setMax(max.get());
        vo.sort();
        return vo;
    }

    private void setData(LineFullSeatRateRankVo vo, String companyLineName,double fullSeatRate) {
        int index=vo.getDataX().indexOf(companyLineName);
        if( index !=-1){
            Integer value = vo.getDataY().get(index);
            vo.getDataY().set(index, (value+ (int)Math.round(fullSeatRate * 100))/2 );
        }else {
            vo.addDataX(companyLineName);
            vo.addDataY((int)Math.round(fullSeatRate * 100));

        }
    }


    @Override
    public Collection<BusAlarmInfoVo> queryBusAlarmInfos(long busId) {
        Optional<BusAlarmInfo> alarmOptional = busAlarmInfoRepository.findById(new BusDateKey(busId));
        Map<String, BusAlarmInfoVo> temp=new LinkedHashMap<>();
        temp.put("drive_offence",new BusAlarmInfoVo(1,"驾驶违规"));

        temp.put("combustible_gas",new BusAlarmInfoVo(2,"可燃物"));
        temp.put("health_epidemic_prevention",new BusAlarmInfoVo(3,"卫生防疫"));
        temp.put("key_person",new BusAlarmInfoVo(4,"重点人员"));
        // temp.put("interference_drive",new BusAlarmInfoVo(4,"干扰驾驶"));
        if(alarmOptional.isPresent()){
            alarmOptional.get().getAlarms().forEach(alarmInfoItem -> {
                BusAlarmInfoVo vo = temp.get(alarmInfoItem.getEventTypeCode());
                if(vo==null){
                    vo=new BusAlarmInfoVo();
                    vo.setId(alarmInfoItem.getEventTypeId());
                    vo.setName(alarmInfoItem.getType());
                }
                vo.addAlarm(alarmInfoItem);
                temp.put(alarmInfoItem.getEventTypeCode(),vo);
            });
        }else {
            List<EventDetailVo> list = logEventDetailDao.queryEventLogListByTypeCode(busId, temp.keySet());
            if(list!=null && !list.isEmpty()){
                Map<Long,AlarmInfoItem> map=new HashMap<>();
                Map<String,Set<Long>> idCode=new HashMap<>();
                for (EventDetailVo vo:list){
                    AlarmInfoItem item = map.get(vo.getEventDetailId());
                    Set<Long> ids = idCode.get(vo.getEventTypeCode());
                    if(ids==null){
                        ids=new HashSet<>();
                    }
                    ids.add(vo.getEventDetailId());
                    if(item==null){
                       item=new AlarmInfoItem();
                        item.setEventTypeId(vo.getEventTypeId());
                        item.setEventTypeCode(vo.getEventTypeCode());
                        item.setType(vo.getEventTypeName());
                        item.setLevel(vo.getEventLevelName());
                        item.setEventDate(vo.getCreatedDate());

                    }
                    String group = vo.getEventTargetGrope();
                    String content = vo.getDescriptionContent();
                    if(!StringUtils.isEmpty(content) && StringUtils.isEmpty(item.getDesc())){
                        Matcher m = AbstractEventHandle.pattern.matcher(content);
                        StringBuffer sb=new StringBuffer();
                        while (m.find()){
                            m.appendReplacement(sb,vo.getEventTargetCode());
                        }
                        m.appendTail(sb);
                        item.setDesc(sb.toString());
                    }
                    if("image".equals(group)){
                        item.addImage(vo.getCollectAttachChar());
                    }
                    map.put(vo.getEventDetailId(),item);
                }
                Set<String> keys = temp.keySet();
                for(String key: keys){
                    Set<Long> ids = idCode.get(key);
                    BusAlarmInfoVo alarmVo = temp.get(key);
                    for(Long id:ids){
                        AlarmInfoItem item = map.get(id);
                        alarmVo.addAlarm(item);
                    }
                }
            }
        }
        return temp.values();
    }

    @Override
    public List<PassengerGenderStatisticsVo> queryPassengerGenderFlowInfoAnalysis(long factoryId) {
        PassengerGenderStatisticsVo maleVo=new PassengerGenderStatisticsVo();
        maleVo.setSexCode(0);

        PassengerGenderStatisticsVo femaleVo=new PassengerGenderStatisticsVo();
        femaleVo.setSexCode(1);

        Optional<PassengerSummary> optional = passengerSummaryRepository.findById(factoryId);
        optional.ifPresent(passengerSummary -> {
            int max=passengerSummary.getFemaleCount() > passengerSummary.getMaleCount()? passengerSummary.getFemaleCount():passengerSummary.getMaleCount();
            femaleVo.setMax(max);
            femaleVo.setNumber(passengerSummary.getFemaleCount());
            maleVo.setMax(max);
            maleVo.setNumber(passengerSummary.getMaleCount());
        });

        List<PassengerGenderStatisticsVo> result=new ArrayList<>();
        result.add(maleVo);
        result.add(femaleVo);
        return result;
    }

    @Override
    public int queryPassengerSum(long factoryId) {
        AtomicInteger passengerCount= new AtomicInteger();
        Optional<PassengerSummary> optional = passengerSummaryRepository.findById(factoryId);
        optional.ifPresent(passengerSummary ->
            passengerCount.set(passengerSummary.getPassengerCount())
        );
        return passengerCount.get();
    }


    /**
     * 保存图片
     * @param fileName
     * @param base64Str
     * @param childPath
     * @return
     */
    public String saveFile(String fileName, String base64Str, String childPath) {
        String filePath = eventFileConfig.getTodayPath() + childPath + fileName;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileCopyUtils.copy(FileUtil.base64UrlToInputStream(base64Str), new BufferedOutputStream(new FileOutputStream(file)));
        } catch (Exception e) {
            logger.error("文件保存失败", e);
        }
//        return eventFileConfig.getFileUrl() + filePath.substring(eventFileConfig.getFilePath().length());
//        return filePath.substring(eventFileConfig.getFilePath().length() + 1);
        return eventFileConfig.getFileRelativePath() + filePath.substring(eventFileConfig.getFilePath().length());
    }

    public static void main(String[] args) {
//        String s1 = "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";
//        String filePath = "C:\\Users\\Buxl\\Downloads\\02.png";
//        try {
//            File file = new File(filePath);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            FileCopyUtils.copy(FileUtil.base64UrlToInputStream(s1), new BufferedOutputStream(new FileOutputStream(file)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(FileUtil.getImgStr("C:\\Users\\Buxl\\Desktop\\1612243281.png"));
    }

}
