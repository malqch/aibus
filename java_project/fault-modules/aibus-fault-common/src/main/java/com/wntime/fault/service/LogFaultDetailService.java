package com.wntime.fault.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.fault.entity.InfoCollectFaultEntity;
import com.wntime.fault.entity.LogFaultDetailEntity;
import com.wntime.fault.form.FaultReportForm;

import java.util.Map;

/**
 * @desc 故障日志表
 *
 * @date 2020-08-25 13:48:11
 */
public interface LogFaultDetailService extends IService<LogFaultDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveFaultDetail(LogFaultDetailEntity logFaultDetailEntity);

    void saveBusFault(FaultReportForm faultReportForm);

    LogFaultDetailEntity getDetailById(Long id);

}

