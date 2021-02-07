package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.dao.LogBusServiceDao;
import com.wntime.customer.region.BusDriveStationInfo;
import com.wntime.customer.repo.BusDriveStationInfoRepository;
import com.wntime.customer.service.LogBusServiceService;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.service.common.entity.LogBusServiceEntity;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import com.wntime.service.common.service.BusServiceLogService;
import com.wntime.service.common.vo.BusPassengerAgeVo;
import com.wntime.service.common.vo.BusPassengerVo;
import com.wntime.service.common.vo.CompanyLinePassengerVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service("logBusServiceService")
public class LogBusServiceServiceImpl extends ServiceImpl<LogBusServiceDao, LogBusServiceEntity> implements LogBusServiceService, BusServiceLogService {

    @Resource
    private BusDriveStationInfoRepository busDriveStationInfoRepository;
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
        Page<LogBusServiceEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if(params.get("companyLine") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("companyLine")))){
            params.put("companyLine", Long.parseLong(String.valueOf(params.get("companyLine"))));
        }else{
            params.put("companyLine", null);
        }

        if(params.get("busStation") != null && StringUtils.isNotEmpty(String.valueOf(params.get("busStation")))){
            params.put("busStation", Long.parseLong(String.valueOf(params.get("busStation"))));
        }else{
            params.put("busStation", null);
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

    @Override
    public List<CompanyLinePassengerVo> queryTodayBusServiceLog(long companyId) {
        return getBaseMapper().queryCompanyBusServiceLog(companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBusServiceLog(long busId, LineStationInfoVo lineStationInfoVo) {
        int count = getBaseMapper().queryBusServiceLogByBusIdAndLineStation(busId, lineStationInfoVo.getCompanyLineId(), lineStationInfoVo.getBusStationId());
        if(count!=0){return;}
        BusDriveStationInfo info=new BusDriveStationInfo();
        info.setBusId(busId);
        info.setCompanyLineId(lineStationInfoVo.getCompanyLineId());
        info.setStationId(lineStationInfoVo.getBusStationId());
        info.setStationStatus(0);
        busDriveStationInfoRepository.save(info);

        LogBusServiceEntity logBusServiceEntity=new LogBusServiceEntity();
        logBusServiceEntity.setBusId(busId);
        logBusServiceEntity.setCompanyLineId(lineStationInfoVo.getCompanyLineId());
        logBusServiceEntity.setCompanyLongitude(lineStationInfoVo.getBusStationLongitude());
        logBusServiceEntity.setCompanytLatitude(lineStationInfoVo.getBusStationLatitude());
        logBusServiceEntity.setBusStationId(lineStationInfoVo.getBusStationId());
        logBusServiceEntity.setNextStationId(lineStationInfoVo.getNextStationId());
        logBusServiceEntity.setIsEnabled(1);
        logBusServiceEntity.setCreatedDate(DateUtils.getTimestamp());
        logBusServiceEntity.setBusGetOn(0);
        logBusServiceEntity.setBusGetOff(0);
        logBusServiceEntity.setBusKeepRide(0);
        logBusServiceEntity.setChildrenNum(0);
        logBusServiceEntity.setChildrenTotal(0);
        logBusServiceEntity.setAdultNum(0);
        logBusServiceEntity.setAdultTotal(0);
        logBusServiceEntity.setOldMun(0);
        logBusServiceEntity.setOldTotal(0);
        logBusServiceEntity.setMaleTotal(0);
        logBusServiceEntity.setFemaleTotal(0);
        save(logBusServiceEntity);
    }

    @Override
    public void disableLatestBusServiceLog(long busId,long companyLineId,long stationId,Long nextStationId) {
        getBaseMapper().disableLastestBusServiceLog(busId, companyLineId, stationId, nextStationId);
        Optional<BusDriveStationInfo> optional = busDriveStationInfoRepository.findById(busId);
        optional.ifPresent(busDriveStationInfo -> {
            busDriveStationInfo.setStationStatus(1);
            busDriveStationInfoRepository.save(busDriveStationInfo);
        });
    }

    @Override
    public BusPassengerVo queryTodayBusPassenger(long busId) {
        return getBaseMapper().queryBusPassengerCount(busId, LocalDate.now());
    }

    @Override
    public BusPassengerAgeVo queryTodayBusPassengerAge(long busId) {
        return getBaseMapper().queryBusPassengerAge(busId,LocalDate.now());
    }

    @Override
    public int queryBusKeepRide(long busId, long companyLineId) {
        Optional<BusRealtimeMonitorStatistics> optional = busRealtimeMonitorStatisticsRepository.findById(new BusDateKey(busId));
        int passengerCount=0;
        if(optional.isPresent()){
            passengerCount=optional.get().getPassengerCount();
        }else {
            Integer count = getBaseMapper().queryBusKeepRide(busId, companyLineId);
            if(count!=null){
                passengerCount=count;
            }
        }
        return passengerCount;
    }

    @Override
    public void removeBusDriveStation(long busId) {
        busDriveStationInfoRepository.deleteById(busId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBusServiceLog(LogBusServiceEntity logBusServiceEntity) {
        //查询数据库是否有公交车线路运营数据
        LogBusServiceEntity oldLogBusService = getBaseMapper().queryByLogByBusIdAndCompanyLineId(logBusServiceEntity.getBusId(), logBusServiceEntity.getCompanyLineId());
        //如果运营数据不为控 则合判断站点是当前还是上一站点
        if(oldLogBusService!=null){
            //如果已有的运营日志和当前录入的站点一致，则合并数据
            if (oldLogBusService.getBusStationId().equals(logBusServiceEntity.getBusStationId())){
                logBusServiceEntity.setBusServiceId(oldLogBusService.getBusServiceId());

                logBusServiceEntity.setBusGetOn(oldLogBusService.getBusGetOn()+logBusServiceEntity.getBusGetOn());
                logBusServiceEntity.setBusGetOff(oldLogBusService.getBusGetOff()+logBusServiceEntity.getBusGetOff());
                logBusServiceEntity.setBusKeepRide(logBusServiceEntity.getBusKeepRide());
                logBusServiceEntity.setChildrenNum(oldLogBusService.getChildrenNum()+logBusServiceEntity.getChildrenNum());
                logBusServiceEntity.setAdultNum(oldLogBusService.getAdultNum()+logBusServiceEntity.getAdultNum());
                logBusServiceEntity.setOldMun(oldLogBusService.getOldMun()+logBusServiceEntity.getOldMun());

                logBusServiceEntity.setChildrenTotal(oldLogBusService.getChildrenTotal()+logBusServiceEntity.getChildrenTotal());
                logBusServiceEntity.setAdultTotal(oldLogBusService.getAdultTotal()+logBusServiceEntity.getAdultTotal());
                logBusServiceEntity.setOldTotal(oldLogBusService.getOldMun()+logBusServiceEntity.getOldTotal());

                logBusServiceEntity.setMaleTotal(oldLogBusService.getMaleTotal()+logBusServiceEntity.getMaleTotal());
                logBusServiceEntity.setFemaleTotal(oldLogBusService.getFemaleTotal()+logBusServiceEntity.getFemaleTotal());
                //终点站，且车上人数为0
                if(logBusServiceEntity.getNextStationId()==null && logBusServiceEntity.getBusKeepRide()==0){
                    logBusServiceEntity.setIsEnabled(Constant.Enabled.DISABLE.getValue());
                }
                updateById(logBusServiceEntity);
            }
        }
    }
}
