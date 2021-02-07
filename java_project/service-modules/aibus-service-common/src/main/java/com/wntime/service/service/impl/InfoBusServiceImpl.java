package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.Assert;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import com.wntime.service.common.repo.BusRealtimeMonitorStatisticsRepository;
import com.wntime.service.common.service.AreaInfoService;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.BusServiceLogService;
import com.wntime.service.common.vo.*;
import com.wntime.service.dao.InfoBusDao;
import com.wntime.service.dao.InfoBusTypeDao;
import com.wntime.service.dao.InfoConfigParamDao;
import com.wntime.service.entity.InfoBusTypeEntity;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.service.region.BusAlarmStatus;
import com.wntime.service.region.BusInfo;
import com.wntime.service.region.BusRealtimeMonitor;
import com.wntime.service.region.BusStatusSummary;
import com.wntime.service.repo.*;
import com.wntime.service.service.InfoBusFactoryService;
import com.wntime.service.service.InfoBusService;
import com.wntime.service.service.InfoConfigParamService;
import com.wntime.service.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("infoBusService")
public class InfoBusServiceImpl extends ServiceImpl<InfoBusDao, InfoBusEntity> implements InfoBusService, BusInfoService {

    @Resource
    private BusCompanyService busCompanyService;
    @Resource
    private InfoBusFactoryService infoBusFactoryService;
    @Resource
    private InfoConfigParamService infoConfigParamService;
    @Resource
    private AreaInfoService areaInfoService;
    @Resource
    private InfoConfigParamDao infoConfigParamDao;
    @Resource
    private BusFaultStatusRepository busFaultStatusRepository;
    @Resource
    private BusAlarmStatusRepository busAlarmStatusRepository;
    @Resource
    private BusInfoRepository busInfoRepository;
    @Resource
    private BusStatusSummaryRepository busStatusSummaryRepository;
    @Resource
    private BusRealtimeMonitorRepository busRealtimeMonitorRepository;
    @Resource
    private BusRealtimeMonitorStatisticsRepository busRealtimeMonitorStatisticsRepository;

    @Resource
    private InfoBusTypeDao infoBusTypeDao;

    @Resource
    private BusServiceLogService busServiceLogService;

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
        Page<InfoBusEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public InfoBusEntity getDetailById(Long id) {
        return getBaseMapper().getDetailInfoById(id);
    }


    @Override
    public void delById(Long id, Long userId) {

        if(getBaseMapper().isBusPlanned(id)){
            throw new RRException("车辆有营运计划安排，不能删除！");
        }
        InfoBusEntity infoBusEntity = new InfoBusEntity();
        infoBusEntity.setBusId(id);
        infoBusEntity.setIsDeleted(1);
        infoBusEntity.setModifiedBy(userId);
        infoBusEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusEntity);
//        BusStatusCodeVo busStatus = getBaseMapper().queryBusStatusAndCompanyId(id);
        BusStatusCodeVo busStatus = null;
        if (busStatus != null) {
            String status = busStatus.getParamCode();
            Optional<BusStatusSummary> summaryOptional = busStatusSummaryRepository.findById(busStatus.getCompanyId());
            summaryOptional.ifPresent(summary -> {
                switch (status) {
                    case "normal":
                        summary.decrementNormalCount();
                        break;
                    case "wait":
                        summary.decrementWaitMaintainCount();
                        break;
                    case "maintenance":
                        summary.decrementMaintainCount();
                        break;
                }
                if (busStatus.getRunStatus() == 2) {
                    summary.decrementFaultCount();
                }
                busStatusSummaryRepository.save(summary);
            });
            busFaultStatusRepository.deleteById(id);
            busInfoRepository.deleteById(id);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public List<InfoBusEntity> getAll() {
        return this.list(new QueryWrapper<InfoBusEntity>()
                .eq("is_deleted", 0)
                .eq("is_enabled", 1));
    }

    @Override
    public List<InfoBusEntity> getAllBusByAreaId(Long areaId, List<Long> companyIdList) {
        return getBaseMapper().getAllBusByAreaId(areaId, companyIdList);
    }

    @Override
    public List<InfoBusEntity> getAllBusByAreaIdListAndCompanyId(List<Long> areaIdList, List<Long> companyIdList) {
        return getBaseMapper().getAllBusByAreaIdListAndCompanyId(areaIdList, companyIdList);
    }

    @Override
    public List<InfoBusEntity> getAllBusByQueryAreaId(Map<String, Object> params) {
        if(params.get("companyId") != null && StringUtils.isNotBlank(String.valueOf(params.get("companyId")))){
            params.put("companyId",Long.parseLong(String.valueOf(params.get("companyId"))));
        }
        return getBaseMapper().getAllBusByQueryAreaId(params);
    }

    @Override
    public List<InfoBusEntity> getAllBusByCompanyId(Long companyId) {
        return getBaseMapper().getBusListByCompanyId(companyId);
    }

    @Override
    public List<StatisticsResultVo> getBusEventStatByAreaId(Map<String, Object> params) {
        Long areaId = Long.parseLong(String.valueOf(params.get("areaId")));
        Assert.isNull(areaId,"地区不能为空");
        List<Long> areaIdList = areaInfoService.getSubAreaIdList(areaId);
        areaIdList.add(areaId);
        List<Long> companyIdList = (List<Long>) params.get("companyIdList");
        List<StatisticsResultVo> statisticsResultVos = new ArrayList<>();
        // 查询区域所有车辆
        List<InfoBusEntity> infoBusEntityList = getAllBusByAreaIdListAndCompanyId(areaIdList, companyIdList);
        if (infoBusEntityList != null && infoBusEntityList.size() > 0) {
            // 根据车辆统计事件
            statisticsResultVos = getBaseMapper().getBusEventStatByAreaId(infoBusEntityList.stream().map
                    (InfoBusEntity::getBusId).collect(Collectors.toList()));
        }else{
            statisticsResultVos.add(new StatisticsResultVo("卡片冒用（次数）","0","卡片冒用"));
            statisticsResultVos.add(new StatisticsResultVo("交通违章（次数）","0","交通违章"));
            statisticsResultVos.add(new StatisticsResultVo("可燃物（次数）","0","可燃物"));
            statisticsResultVos.add(new StatisticsResultVo("重点人员（次数）","0","重点人员"));
        }
        return statisticsResultVos;
    }

    @Override
    public List<String> getVinListByFaultTypeIdAndCompanyId(Long faultTypeId, Long companyId) {

        return getBaseMapper().getVinListByFaultTypeIdAndCompanyId(faultTypeId, companyId);
    }

    @Override
    public BusBaseInfoVo queryBusInfo(long busId) {
        BusInfo busInfo = busInfoRepository.findById(busId).orElse(new BusInfo());
        BusBaseInfoVo vo = new BusBaseInfoVo();
        BeanUtils.copyProperties(busInfo, vo);
        return vo;
    }

    @Override
    public InfoBusEntity saveImportBusInfo(OrderBusDeliveryBatchForm orderBusDeliveryBatchForm) {
        BusVo busInfo = this.getBaseMapper().queryBusByVin(orderBusDeliveryBatchForm.getVinCode());
        if(busInfo != null){
            throw  new RRException("该[VIN码:"+orderBusDeliveryBatchForm.getVinCode()+"]车辆信息已存在");
        }
        InfoBusEntity infoBusEntity = new InfoBusEntity();
        // TODO 车厂
        infoBusEntity.setFactoryId(infoBusFactoryService.getAll().get(0).getFactoryId());
        infoBusEntity.setBusTypeId(orderBusDeliveryBatchForm.getBusTypeId());
        infoBusEntity.setVinCode(orderBusDeliveryBatchForm.getVinCode());
        infoBusEntity.setIsDeleted(0);
        infoBusEntity.setIsEnabled(1);
        // 正常状态ID
        InfoConfigParamEntity infoConfigParamEntity = infoConfigParamService.getOne(
                new QueryWrapper<InfoConfigParamEntity>()
                        .eq("is_deleted",0)
                        .eq("is_enabled",1)
                        .eq("param_group","bus_status")
                        .eq("param_code","normal")
        );
        if(infoConfigParamEntity !=null){
            infoBusEntity.setBusStatus(infoConfigParamEntity.getConfigParamId());
        }

        infoBusEntity.setRunStatus(1);
        infoBusEntity.setCreatedBy(orderBusDeliveryBatchForm.getUserId());
        infoBusEntity.setCreatedDate(DateUtils.getTimestamp());
        this.save(infoBusEntity);
        return infoBusEntity;
    }


    @Override
    public List<String> getVinListByBusStatusAndCompanyId(Long busStatus, Long companyId) {
        return getBaseMapper().getVinListByBusStatusAndCompanyId(busStatus, companyId);
    }

    @Override
    public List<String> getVinListByCompanyId(Long companyId) {
        return getBaseMapper().getVinListByCompanyId(companyId);
    }

    @Override
    public List<String> getPlateCodeListByCompanyId(Long companyId) {
        return getBaseMapper().getPlateCodeListByCompanyId(companyId);
    }

    @Override
    public List<InfoBusEntity> getVinNoDeliveryList() {
        return getBaseMapper().getVinNoDeliveryList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBus(InfoBusEntity infoBusEntity) {
        infoBusEntity.setRunStatus(1);
        save(infoBusEntity);
        if(Constant.Enabled.ENABLE.getValue()==infoBusEntity.getIsEnabled()){
            BusInfo busInfo = new BusInfo();
            busInfo.setBusId(infoBusEntity.getBusId());
            busInfo.setVinCode(infoBusEntity.getVinCode());
            busInfo.setFactoryId(infoBusEntity.getFactoryId());

            InfoBusTypeEntity busType = infoBusTypeDao.selectById(infoBusEntity.getBusTypeId());
            busInfo.setPeopleLoadCount(busType.getPeopleNumber());
//            busInfoRepository.save(busInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBus(InfoBusEntity infoBusEntity) {
        //禁用车辆时要校验车辆有没有营运计划
        if(infoBusEntity.getIsEnabled().equals(Constant.Enabled.DISABLE.getValue()) && getBaseMapper().isBusPlanned(infoBusEntity.getBusId())){
            throw new RRException("车辆有营运计划安排，不能禁用！");
        }
        infoBusEntity.setRunStatus(1);
        updateById(infoBusEntity);

//        if (Constant.Deleted.DELETED.getValue() == infoBusEntity.getIsDeleted()
//                || Constant.Enabled.DISABLE.getValue() == infoBusEntity.getIsEnabled()) {
//            busInfoRepository.deleteById(infoBusEntity.getBusId());
//        } else if (Constant.Deleted.UNDELETED.getValue() == infoBusEntity.getIsDeleted()
//                && Constant.Enabled.ENABLE.getValue() == infoBusEntity.getIsEnabled()) {
//            Optional<BusInfo> optional = busInfoRepository.findById(infoBusEntity.getBusId());
//            optional.ifPresent(busInfo->{
//                busInfo.setVinCode(infoBusEntity.getVinCode());
//                busInfo.setFactoryId(infoBusEntity.getFactoryId());
//                InfoBusTypeEntity busType = infoBusTypeDao.selectById(infoBusEntity.getBusTypeId());
//                busInfo.setPeopleLoadCount(busType.getPeopleNumber());
//                busInfoRepository.save(busInfo);
//            });
//        }
    }

    @Override
    public void saveBusInfo(BusBaseInfoVo busBaseInfoVo) {
        Optional<BusInfo> busOptional = busInfoRepository.findById(busBaseInfoVo.getBusId());
        BusInfo busInfo;

        if(busOptional.isPresent()){
            busInfo=busOptional.get();
        }else {
            busInfo=new BusInfo();
            Optional<BusStatusSummary> optional = busStatusSummaryRepository.findById(busBaseInfoVo.getCompanyId());
            BusStatusSummary busStatusSummary = optional.orElse(new BusStatusSummary(busBaseInfoVo.getCompanyId()));
            busStatusSummary.incrementBusCount();
            busStatusSummary.incrementNormalCount();
            busStatusSummaryRepository.save(busStatusSummary);
        }

        BeanUtils.copyProperties(busBaseInfoVo,busInfo);
        InfoBusEntity bus = getBaseMapper().selectById(busInfo.getBusId());
        busInfo.setVinCode(bus.getVinCode());
        busInfo.setPeopleLoadCount(getBaseMapper().queryBusLoadCount(busInfo.getBusId()));
        busInfoRepository.save(busInfo);
    }

    @Override
    public void incrementFaultBusStatusSummary(long companyId) {
        Optional<BusStatusSummary> summaryOptional = busStatusSummaryRepository.findById(companyId);
        summaryOptional.ifPresent(busStatusSummary -> {
            busStatusSummary.decrementNormalCount();
            busStatusSummary.incrementFaultCount();
            busStatusSummaryRepository.save(busStatusSummary);
        });
    }

    @Override
    public void incrementNormalBusStatusSummary(long companyId) {
        Optional<BusStatusSummary> summaryOptional = busStatusSummaryRepository.findById(companyId);
        summaryOptional.ifPresent(busStatusSummary -> {
            busStatusSummary.incrementNormalCount();
            busStatusSummary.decrementFaultCount();
            busStatusSummaryRepository.save(busStatusSummary);
        });
    }

    @Override
    public void updateBusInfoStatus(long busId, int busStatus) {
        Optional<BusInfo> optional = busInfoRepository.findById(busId);
        optional.ifPresent(busInfo->{
            busInfo.setStatus(busStatus);
            busInfoRepository.save(busInfo);
        });
        if(busStatus==1){
            BusAlarmStatus busAlarmStatus=new BusAlarmStatus(busId);
            busAlarmStatusRepository.save(busAlarmStatus);
        }
    }

    @Override
    public void decrementBusStatusSummaryCount(long companyId,long busId) {
        Optional<BusStatusSummary> summaryOptional = busStatusSummaryRepository.findById(companyId);
        summaryOptional.ifPresent(busStatusSummary->{
            BusStatusCodeVo busStatus = getBaseMapper().queryBusStatusAndCompanyId(busId);

            if (busStatus.getParamCode().equals("normal")) {
                busStatusSummary.decrementNormalCount();
            } else if (busStatus.getParamCode().equals("wait")) {
                busStatusSummary.decrementWaitMaintainCount();
            } else if (busStatus.getParamCode().equals("maintenance")) {
                busStatusSummary.decrementMaintainCount();
            }
            if(busStatus.getRunStatus()==2){
                busStatusSummary.decrementFaultCount();
            }
            busStatusSummaryRepository.save(busStatusSummary);
        });
    }

    @Override
    public void checkBusNormal(long busId) {
        ConfigParamVo config = infoConfigParamDao.queryConfigParamByGroupCode("bus_status", "normal");
        if(config!=null){
            Integer count = getBaseMapper().selectCount(new QueryWrapper<InfoBusEntity>()
                    .eq("bus_id", busId)
                    .eq("bus_status", config.getConfigParamId())
                    .eq("is_deleted", 0).eq("is_enabled", 1));
            if(count!=1){
                throw new RRException("该车辆无法上报故障");
            }
        }
    }

    @Override
    public List<BusInfoVo> queryWithFuzzyMatch(long factoryId, String keyWord, int size) {
        return getBaseMapper().queryBusInfoByVinOrPlateCode(factoryId, keyWord, size);
    }

    @Override
    public BusRealtimeStatisticsVo queryBusMonitorStatistics(long busId) {
        BusRealtimeStatisticsVo vo = new BusRealtimeStatisticsVo(busId);
        Optional<BusRealtimeMonitorStatistics> optional = busRealtimeMonitorStatisticsRepository.findById(new BusDateKey(busId));
        optional.ifPresent(busRealtimeMonitorStatistics -> {
            vo.setFullSeatRate(busRealtimeMonitorStatistics.getFullSeatRate());
            vo.setKeyPersonDiscernCount(busRealtimeMonitorStatistics.getKeyPersonDiscernCount());
            vo.setGas(busRealtimeMonitorStatistics.getGas());
            vo.setAiFace(busRealtimeMonitorStatistics.getAiFace());

            vo.setDistracted(busRealtimeMonitorStatistics.getDistracted());
            vo.setFatigueDriver(busRealtimeMonitorStatistics.getFatigueDriver());
            vo.setTemperatureAbnormal(busRealtimeMonitorStatistics.getTemperatureAbnormal());
        });

        Optional<BusRealtimeMonitor> monitorOptional = busRealtimeMonitorRepository.findById(new BusDateKey(busId));
        monitorOptional.ifPresent(busRealtimeMonitor -> {
            vo.setBatteryEnergyRatio(busRealtimeMonitor.getBusBatteryEnergy());
            vo.setBusMotorStatus(busRealtimeMonitor.getBusMotorStatus());
            vo.setBusSpeed(busRealtimeMonitor.getBusSpeed());
        });
        return vo;
    }

    @Override
    public BusDrivingBaseDataVo queryBusDrivingData(long busId) {
        BusDrivingBaseDataVo vo = new BusDrivingBaseDataVo();
        Optional<BusRealtimeMonitor> optional = busRealtimeMonitorRepository.findById(new BusDateKey(busId));
        optional.ifPresent(busRealtimeMonitor -> {
            vo.setBusTotalMile(busRealtimeMonitor.getBusTotalMile());
            vo.setBusSpeed(busRealtimeMonitor.getBusSpeed());
            vo.setBatteryEnergyRatio(busRealtimeMonitor.getBusBatteryEnergy());
            vo.setBusMotorStatus(busRealtimeMonitor.getBusMotorStatus());
        });

        Optional<BusInfo> busOptional = busInfoRepository.findById(busId);
        if(busOptional.isPresent()){
            BusInfo busInfo = busOptional.get();
            vo.setBusId(busInfo.getBusId());
            vo.setVinCode(busInfo.getVinCode());
            vo.setPlateCode(busInfo.getPlateCode());
        }else{
            BusVo busVo = getBaseMapper().queryBusInfo(busId);
            if(busVo!=null) {
                vo.setBusId(busVo.getBusId());
                vo.setVinCode(busVo.getVinCode());
                vo.setPlateCode(busVo.getPlateCode());
            }
        }
        return vo;
    }

    @Override
    public PageUtils<AfterSalesBusInfoVO> getBusPageByStatusAndCompanyIdAndVin
            (Long busStatusId, Long companyId, String vin, Integer currentPage, Integer pageSize) {

        Page<AfterSalesBusInfoVO> page = new Page<>(currentPage, pageSize);
        Map<String, Object> params = new HashMap<>(4);
        params.put("page", page);
        params.put("busStatusId", busStatusId);
        params.put("companyId", companyId);
        params.put("vin", vin);
        return new PageUtils<>(page.setRecords(getBaseMapper().getBusListByStatusAndCompanyIdAndVin(params)));
    }

    @Override
    public PageUtils<AfterSalesMaintainBusInfoVO> getMaintainBusPageAndCompanyIdAndVin
            (Long busStatusId, Long companyId, String vin, Integer currentPage, Integer pageSize) {
        Page<AfterSalesMaintainBusInfoVO> page = new Page<>(currentPage, pageSize);
        Map<String, Object> params = new HashMap<>(4);
        params.put("page", page);
        params.put("busStatusId", busStatusId);
        params.put("companyId", companyId);
        params.put("vin", vin);
        return new PageUtils<>(page.setRecords(getBaseMapper().getMaintainBusListAndCompanyIdAndVin(params)));
    }

    @Override
    public AfterSalesMaintainBusInfoVO getAfterSalesMaintainBusInfo(String vin) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("vin", vin);
        List<AfterSalesMaintainBusInfoVO> maintainBusList = getBaseMapper().getMaintainBusListAndCompanyIdAndVin(params);
        Assert.isEmpty(maintainBusList, "没有数据");
        return maintainBusList.get(0);

    }

    @Override
    public AfterSalesBusInfoVO getAfterSalesBusInfo(String vin) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("vin", vin);
        List<AfterSalesBusInfoVO> busList = getBaseMapper().getBusListByStatusAndCompanyIdAndVin(params);
        Assert.isEmpty(busList, "没有数据");
        return busList.get(0);
    }

    @Override
    public List<BusStatusVO> getBusStatusList() {
        return getBaseMapper().getBusStatusList();
    }

    @Override
    public BusStatusVO getBusStatusByCode(String busStatusCode) {
        return getBaseMapper().getBusStatusByCode(busStatusCode);
    }

    @Override
    public BusStatusVO getBusStatusById(Long busStatusId) {
        return getBaseMapper().getBusStatusById(busStatusId);
    }

    @Override
    public int changeBusStatus(Long busId, Long busStatusId) {
        InfoConfigParamEntity param = infoConfigParamDao.selectById(busStatusId);

        updateBusCount(busId,param.getParamCode());
        int count = getBaseMapper().changeBusStatus(busId, busStatusId);

        if("normal".equals(param.getParamCode())){
            updateBusInfoStatus(busId,0);
        }
        return count;
    }

    @Override
    @DataSource("read")
    public BusVo queryBusByVin(String vinCode) {
        return getBaseMapper().queryBusByVin(vinCode);
    }

    @Override
    public long getBusCountByCompanyId(Long companyId) {
        return getBaseMapper().getBusCountByCompanyId(companyId);
    }

    @Override
    public List<ProgressBarItem> getBusStatus(Long busCompanyId) {
        List<ProgressBarItem> result = new ArrayList<>();
        Optional<BusStatusSummary> optional = busStatusSummaryRepository.findById(busCompanyId);
        if(optional.isPresent()){
            ProgressBarItem normalVo = new ProgressBarItem();
            normalVo.setId(1L);
            normalVo.setNumber(optional.get().getNormalCount());
            normalVo.setName("正常运行车辆");
            result.add(normalVo);

            ProgressBarItem alarmVo = new ProgressBarItem();
            alarmVo.setId(2L);
            alarmVo.setNumber(optional.get().getFaultCount());
            alarmVo.setName("故障告警车辆");
            result.add(alarmVo);

            ProgressBarItem waitVo = new ProgressBarItem();
            waitVo.setId(3L);
            waitVo.setNumber(optional.get().getWaitMaintainCount());
            waitVo.setName("待修车辆");
            result.add(waitVo);

            ProgressBarItem maintainVo = new ProgressBarItem();
            maintainVo.setId(4L);
            maintainVo.setNumber(optional.get().getMaintainCount());
            maintainVo.setName("维修中车辆");
            result.add(maintainVo);
        }else{
            ProgressBarItem normalVo = new ProgressBarItem();
            normalVo.setId(1L);
            normalVo.setNumber(0);
            normalVo.setName("正常运行车辆");
            result.add(normalVo);

            ProgressBarItem alarmVo = new ProgressBarItem();
            alarmVo.setId(2L);
            alarmVo.setNumber(0);
            alarmVo.setName("故障告警车辆");
            result.add(alarmVo);

            ProgressBarItem waitVo = new ProgressBarItem();
            waitVo.setId(3L);
            waitVo.setNumber(0);
            waitVo.setName("待修车辆");
            result.add(waitVo);

            ProgressBarItem maintainVo = new ProgressBarItem();
            maintainVo.setId(4L);
            maintainVo.setNumber(0);
            maintainVo.setName("维修中车辆");
            result.add(maintainVo);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> countByRunStatusAndCompanyId(Long companyId) {
        return getBaseMapper().countByRunStatusAndCompanyId(companyId);
    }

    @Override
    public List<Map<String, Object>> countByBusStatusAndCompanyId(Long companyId) {

        return getBaseMapper().countByBusStatusAndCompanyId(companyId);
    }


    @Override
    public void updateBusCount(long busId, String newStatus) {
        BusStatusCodeVo busStatus = getBaseMapper().queryBusStatusAndCompanyId(busId);
        String oldStatus = busStatus.getParamCode();
        //如果车辆状态为维修时 清除车辆运行站点数据
        if(newStatus.equals("wait") || newStatus.equals("maintenance")){
            busServiceLogService.removeBusDriveStation(busId);
        }
        if (!newStatus.equals(oldStatus) ) {
            Optional<BusStatusSummary> summaryOptional = busStatusSummaryRepository.findById(busStatus.getCompanyId());
            summaryOptional.ifPresent(summary -> {

                if (oldStatus.equals("normal")) {
                    summary.decrementNormalCount();
                } else if (oldStatus.equals("wait")) {
                    summary.decrementWaitMaintainCount();
                } else if (oldStatus.equals("maintenance")) {
                    summary.decrementMaintainCount();
                }

                if (newStatus.equals("normal")) {
                    summary.incrementNormalCount();

                } else if (newStatus .equals("wait")) {
                    summary.incrementWaitMaintainCount();
                } else if (newStatus.equals("maintenance")) {
                    summary.incrementMaintainCount();
                }

                busStatusSummaryRepository.save(summary);

            });
        }
    }


    @Override
    public Map<String,Object> queryBusStatusSummary(long busCompanyId) {
        Map<String,Object> map=new HashMap<>();
        Optional<BusStatusSummary> optional = busStatusSummaryRepository.findById(busCompanyId);
        List<BusStatusSummaryItemVo> result = new ArrayList<>();
        BusStatusSummaryItemVo normalVo = new BusStatusSummaryItemVo();
        normalVo.setBusStatus(0);
        normalVo.setName("正常运行车辆");

        BusStatusSummaryItemVo alarmVo = new BusStatusSummaryItemVo();
        alarmVo.setBusStatus(1);
        alarmVo.setName("故障告警车辆");

        BusStatusSummaryItemVo waitVo = new BusStatusSummaryItemVo();
        waitVo.setBusStatus(2);
        waitVo.setName("待修车辆");

        BusStatusSummaryItemVo maintainVo = new BusStatusSummaryItemVo();
        maintainVo.setBusStatus(3);
        maintainVo.setName("维修中车辆");

        if(optional.isPresent()){
            BusStatusSummary busStatusSummary = optional.get();
            normalVo.setMax(busStatusSummary.getBusCount());
            normalVo.setNumber(busStatusSummary.getNormalCount());

            alarmVo.setMax(busStatusSummary.getBusCount());
            alarmVo.setNumber(busStatusSummary.getFaultCount());

            waitVo.setMax(busStatusSummary.getBusCount());
            waitVo.setNumber(busStatusSummary.getWaitMaintainCount());

            maintainVo.setMax(busStatusSummary.getBusCount());
            maintainVo.setNumber(busStatusSummary.getMaintainCount());
        }else {
            List<BusStatusCountVo> list = getBaseMapper().queryCompanyBusStatus(busCompanyId);
            int busCount = busCompanyService.queryCompanyBusCount(busCompanyId);
            normalVo.setMax(busCount);
            alarmVo.setMax(busCount);
            waitVo.setMax(busCount);
            maintainVo.setMax(busCount);

            if(list!=null && !list.isEmpty()){
                for(BusStatusCountVo vo: list ){
                    if("normal".equals(vo.getBusStatus())){
                        normalVo.setNumber(vo.getStatusCount());
                    }else if("wait".equals(vo.getBusStatus())){
                        waitVo.setNumber(vo.getStatusCount());
                    }else if("maintenance".equals(vo.getBusStatus())){
                        maintainVo.setNumber(vo.getStatusCount());
                    }
                }
            }

            BusStatusSummary busStatusSummary=new BusStatusSummary(busCompanyId);
            busStatusSummary.setBusCount(busCount);
            busStatusSummary.setNormalCount(normalVo.getNumber());
            busStatusSummary.setWaitMaintainCount(waitVo.getNumber());
            busStatusSummary.setMaintainCount(maintainVo.getNumber());
            busStatusSummaryRepository.save(busStatusSummary);
        }

        result.add(normalVo);
        result.add(alarmVo);
        result.add(waitVo);
        result.add(maintainVo);


        StringBuilder tips=new StringBuilder();
        if (normalVo.getNumber() == normalVo.getMax()) {
            ConfigParamVo config = infoConfigParamDao.queryConfigParamByGroupCode("normal_ratio_knowledge", "normal");
            if(config!=null){
                tips.append(config.getParamChar());
            }
        }else {
            tips.append(analyse(alarmVo.getNumber(),alarmVo.getMax(),"fault_ratio_knowledge"));
            tips.append(analyse(waitVo.getNumber(),waitVo.getMax(),"wait_maintain_ratio_knowledge"));
            tips.append(analyse(maintainVo.getNumber(),maintainVo.getMax(),"maintain_ratio_knowledge"));
        }
        map.put("list",result);
        map.put("tips",tips.toString());
        return map;
    }
    public String analyse(int count ,int total,String group){
        String tips="";
        if(count==0){
            return tips;
        }
        double ratio = new BigDecimal(count).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        List<ConfigParamVo> list = infoConfigParamDao.queryParamsByGroup(group);

        if(list!=null && !list.isEmpty()){
            for(ConfigParamVo vo: list){
                if ( ratio<= vo.getParamValue()  && vo.getParamValue() != 0.0) {
                    tips=vo.getParamChar();
                }
            }
        }
        return tips;
    }

    @Override
    public void updateBusCompany(long busId, long companyId) {
        Optional<BusInfo> optional = busInfoRepository.findById(busId);
        optional.ifPresent(bus->{
            bus.setCompanyId(companyId);
            busInfoRepository.save(bus);
        });
    }
}
