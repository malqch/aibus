package com.wntime.maintain.service.impl;

import com.wntime.common.utils.DateUtils;
import com.wntime.common.validator.Assert;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.maintain.service.MaintenanceService;
import com.wntime.maintain.vo.MaintainBusVO;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.vo.BusLineVo;
import com.wntime.service.common.vo.BusStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.maintain.dao.LogBusMaintenanceDao;
import com.wntime.maintain.entity.LogBusMaintenanceEntity;
import com.wntime.maintain.service.LogBusMaintenanceService;
import org.springframework.transaction.annotation.Transactional;


@Service("logBusMaintenanceService")
public class LogBusMaintenanceServiceImpl extends ServiceImpl<LogBusMaintenanceDao, LogBusMaintenanceEntity> implements LogBusMaintenanceService, MaintenanceService {

    @Autowired
    private BusInfoService busInfoService;
    @Autowired
    private CompanyLineService companyLineService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogBusMaintenanceEntity> page = this.page(
                new Query<LogBusMaintenanceEntity>().getPage(params),
                new QueryWrapper<LogBusMaintenanceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MaintainBusVO> getMaintainBusDetailByStatusAndCompanyIdAndTime(Long status, Long companyId,Date startTime, Date endTime) {
        return getBaseMapper().getMaintainBusDetailByStatusAndCompanyIdAndTime(status,companyId,startTime,endTime);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int repairBus(Long busId, Long busStatusId, LogBusMaintenanceEntity logBusMaintenance) {
        //修改状态
        BusStatusVO busStatusVO = busInfoService.getBusStatusById(busStatusId);
        Assert.isNull(busStatusVO,"车辆状态ID不合法！");

        busInfoService.changeBusStatus(busId,busStatusId);

        //记录维保日志
        BusLineVo busLineVo = companyLineService.queryBusLine(busId);
        logBusMaintenance.setBusId(busId);
        if(busLineVo != null) {
            logBusMaintenance.setCompanyLineId(busLineVo.getCompanyLineId());
        }
        logBusMaintenance.setCreatedDate(DateUtils.getTimestamp());
        logBusMaintenance.setBusStatus(busStatusId);
        return getBaseMapper().insert(logBusMaintenance);
    }


    @Override
    @DataSource("read")
    public int queryFactoryBusMaintenanceCount(long factoryId) {
        return getBaseMapper().queryBusMaintenanceCount(factoryId);
    }
}
