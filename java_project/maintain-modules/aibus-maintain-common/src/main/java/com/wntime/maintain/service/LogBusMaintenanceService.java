package com.wntime.maintain.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.maintain.entity.LogBusMaintenanceEntity;
import com.wntime.maintain.vo.MaintainBusVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc 维保日志表
 *
 * @date 2020-08-25 14:00:24
 */
public interface LogBusMaintenanceService extends IService<LogBusMaintenanceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MaintainBusVO> getMaintainBusDetailByStatusAndCompanyIdAndTime(Long status, Long companyId,Date startTime, Date endTime);

    int repairBus(Long busId, Long busStatusId, LogBusMaintenanceEntity logBusMaintenance);
}

