package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.entity.LogBusDriveEntity;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import com.wntime.service.common.service.BusDriveLogService;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.vo.BusRealtimeMonitorVo;
import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.dao.InfoBusDao;
import com.wntime.service.dao.InfoConfigParamDao;
import com.wntime.service.dao.LogBusDriveDao;
import com.wntime.service.region.BusFaultStatus;
import com.wntime.service.region.BusInfo;
import com.wntime.service.region.BusRealtimeMonitor;
import com.wntime.service.repo.BusFaultStatusRepository;
import com.wntime.service.repo.BusInfoRepository;
import com.wntime.service.repo.BusRealtimeMonitorRepository;
import com.wntime.service.service.LogBusDriveService;
import org.apache.commons.lang.StringUtils;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.beans.BeanUtils;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;


@Service("logBusDriveService")
public class LogBusDriveServiceImpl extends ServiceImpl<LogBusDriveDao, LogBusDriveEntity> implements LogBusDriveService, BusDriveLogService {

    @Resource
    private GemFireCache gemFireCache;
    @Resource
    private InfoBusDao infoBusDao;
    @Resource
    private BusInfoService busInfoService;
    @Resource
    private InfoConfigParamDao infoConfigParamDao;
    @Resource
    private BusInfoRepository busInfoRepository;
    @Resource
    private BusFaultStatusRepository busFaultStatusRepository;
    @Resource
    private BusRealtimeMonitorRepository busRealtimeMonitorRepository;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }
        Page<LogBusDriveEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if(params.get("busStatusId") != null && StringUtils.isNotEmpty(String.valueOf(params.get("busStatusId")))){
            params.put("busStatusId", Long.parseLong(String.valueOf(params.get("busStatusId"))));
        }

        if(params.get("motorStatusId") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("motorStatusId")))){
            params.put("motorStatusId", Long.parseLong(String.valueOf(params.get("motorStatusId"))));
        }

        if(params.get("batteryStatusId") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("batteryStatusId")))){
            params.put("batteryStatusId", Long.parseLong(String.valueOf(params.get("batteryStatusId"))) );
        }

        if(params.get("busTotalMileMin") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("busTotalMileMin")))){
            params.put("busTotalMileMin", Double.parseDouble(String.valueOf(params.get("busTotalMileMin"))) );
        }

        if(params.get("busTotalMileMax") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("busTotalMileMax")))){
            params.put("busTotalMileMax", Double.parseDouble(String.valueOf(params.get("busTotalMileMax"))) );
        }

        if(params.get("startTime") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("startTime")))){
            params.put("startTime", DateUtils.stringToDate(String.valueOf(params.get("startTime")),DateUtils.DATE_TIME_PATTERN));
        }else{
            params.put("startTime",null);
        }

        if(params.get("endTime") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("endTime")))){
            params.put("endTime", DateUtils.stringToDate(String.valueOf(params.get("endTime")),DateUtils.DATE_TIME_PATTERN) );
        }else{
            params.put("endTime",null);
        }
        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }


    /**
     * @param logBusDriveEntity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBusDriveLog(LogBusDriveEntity logBusDriveEntity) {
        if(logBusDriveEntity.getBusBatteryVoltage()<=0.0){
            return;
        }
        Long busId = logBusDriveEntity.getBusId();
        InfoBusEntity bus = infoBusDao.selectById(busId);
        if(bus==null){throw new RRException("公交车信息无效");}
        ConfigParamVo motorStatus = infoConfigParamDao.queryConfigParamByGroupValue("motor_status", (double) logBusDriveEntity.getBusMotorStatus());
        ConfigParamVo batteryStatus = infoConfigParamDao.queryConfigParamByGroupValue("battery_status", (double) logBusDriveEntity.getBusBatteryStatus());
        ConfigParamVo carStatus = infoConfigParamDao.queryConfigParamByGroupValue("car_status", (double) logBusDriveEntity.getBusStatus());
        int busStatus = Integer.valueOf(String.valueOf(logBusDriveEntity.getBusStatus()));
        Integer motorStatusInt = Integer.valueOf(String.valueOf(logBusDriveEntity.getBusMotorStatus()));
        Integer batteryStatusInt = Integer.valueOf(String.valueOf(logBusDriveEntity.getBusBatteryStatus()));
        logBusDriveEntity.setBusStatus(carStatus.getConfigParamId());
        logBusDriveEntity.setBusBatteryStatus(batteryStatus.getConfigParamId());
        logBusDriveEntity.setBusMotorStatus(motorStatus.getConfigParamId());
        save(logBusDriveEntity);

        BusRealtimeMonitor busMonitor=new BusRealtimeMonitor(busId);
        BeanUtils.copyProperties(logBusDriveEntity, busMonitor);
        busMonitor.setBusStatus(busStatus);
        busMonitor.setBusMotorStatus(motorStatusInt);
        busMonitor.setBusBatteryStatus(batteryStatusInt);
        busRealtimeMonitorRepository.save(new Wrapper<>(busMonitor, busMonitor.getKey()));
    }

    @Override
    public void busDriveStatus(long busId,int busStatus){
        infoBusDao.updateCarStatus(busId, busStatus);

        BusDateKey key = new BusDateKey(busId);
        Optional<BusRealtimeMonitorStatistics> statisticsOptional = busRealtimeMonitorStatisticsRepository.findById(key);
        BusRealtimeMonitorStatistics statistics;

        if (!statisticsOptional.isPresent()) {
            statistics = new BusRealtimeMonitorStatistics(key);
            int loadCount = infoBusDao.queryBusLoadCount(busId);
            statistics.setPeopleLoadCount(loadCount);
            busRealtimeMonitorStatisticsRepository.save(new Wrapper<>(statistics, statistics.getKey()));
        }

        Optional<BusInfo> busInfoOptional = busInfoRepository.findById(busId);
        //故障
        if(busStatus == 0){
            busInfoOptional.ifPresent(busInfo -> {
                BusFaultStatus busFaultStatus = new BusFaultStatus(busId);
                busFaultStatus.setCompanyId(busInfo.getCompanyId());
                busFaultStatusRepository.save(busFaultStatus);
                //当车辆状态不为故障时 才会增加数量
                if (busInfo.getStatus() != 2) {
                    //增加公交公司的故障告警车辆数
                    busInfoService.incrementFaultBusStatusSummary(busInfo.getCompanyId());
                    busInfo.setStatus(2);
                    busInfoRepository.save(busInfo);
                }
            });
        }else if(busStatus==1){
            busFaultStatusRepository.findById(busId).ifPresent(busFaultStatus -> {
                busFaultStatusRepository.deleteById(busId);
                busInfoService.incrementNormalBusStatusSummary(busFaultStatus.getCompanyId());
                busInfoOptional.ifPresent(busInfo -> {
                    busInfo.setStatus(0);
                    busInfoRepository.save(busInfo);
                });
            });
        }
    }

    @Override
    public BusRealtimeMonitorVo queryByBusId(long busId) {
        LocalDate now=LocalDate.now();
        BusRealtimeMonitor bus =findMonitorData(busId,now);
        BusRealtimeMonitorVo vo = new BusRealtimeMonitorVo();
        vo.setBusId(busId);
        vo.setBatteryEnergyRatio(bus.getBusBatteryEnergy()==null?0:bus.getBusBatteryEnergy());
        vo.setBusMotorStatus(bus.getBusMotorStatus());
        vo.setBusSpeed(bus.getBusSpeed() == null ? 0.0 : bus.getBusSpeed());
        vo.setBusTotalMile(bus.getBusTotalMile() == null ? 0.0 : bus.getBusTotalMile());
        vo.setSurplusMile(bus.getSurplusMile() == null ? 0.0 : bus.getSurplusMile());
        vo.setFactoryLongitude(bus.getFactoryLongitude() == null ? 0.0 : bus.getFactoryLongitude());
        vo.setFactoryLatitude(bus.getFactoryLatitude() == null ? 0.0 : bus.getFactoryLatitude());
        vo.setBusStatus(bus.getBusStatus());
        vo.setBusBatteryVoltage(bus.getBusBatteryVoltage() == null ? 0.0 : bus.getBusBatteryVoltage());
        vo.setBusBatteryCurrent(bus.getBusBatteryCurrent() == null ? 0.0 : bus.getBusBatteryCurrent());
        vo.setBusBatteryTemperature(bus.getBusBatteryTemperature() == null ? 0.0 : bus.getBusBatteryTemperature());
        vo.setBusBatteryStatus(bus.getBusBatteryStatus());
        return vo;
    }

    @Override
    public int queryBusTotalMileSum(long factoryId) {
        Double sum = getBaseMapper().queryFactoryBusTotalMileSum(factoryId);
        return sum != null ? sum.intValue() : 0;
    }

    private BusRealtimeMonitor findMonitorData(long busId, LocalDate localDate){
        if(localDate.compareTo(LocalDate.now().plusDays(-7))==0){
            return new BusRealtimeMonitor(busId);
        }
        Optional<BusRealtimeMonitor> optional = getBusMonitor(busId,localDate);
        if(optional.isPresent()){
            return optional.get();
        }else {
            return findMonitorData(busId,localDate.plusDays(-1));
        }
    }
    private Optional<BusRealtimeMonitor> getBusMonitor(long busId,LocalDate localDate){
        Optional<BusRealtimeMonitor> optional = busRealtimeMonitorRepository.findById(new BusDateKey(busId,localDate));
        if(optional.isPresent()){
            return optional;
        }else {
            return getBaseMapper().queryLogBusDerive(busId,localDate);
        }
    }
}
