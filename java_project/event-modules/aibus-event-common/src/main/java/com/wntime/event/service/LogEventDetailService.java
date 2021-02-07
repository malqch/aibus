package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.LogEventDetailEntity;
import com.wntime.event.vo.*;
import com.wntime.service.common.vo.StatisticsResultVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc 事件日志表
 *
 * @date 2020-08-25 13:34:24
 */
public interface LogEventDetailService extends IService<LogEventDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String getParamConfigStationName();

    PageUtils<EventHealthTempStatVo> getTempStatByCompanyAndLinePage(Map<String, Object> params);

    PageUtils<EventTrafficStatVo> getTrafficStatByCompanyPage(Map<String, Object> params);

    List<EventTrafficStatVo> getTrafficStatByCompanyPageExport(Map<String, Object> params);

    List<StatisticsResultVo> getTempDetailByEventDetailId(List<Long> eventDetailIdList);

    List<Map<String,Object>> getTrafficEventCountByCompanyIdGroupByBus(Long companyId, Date startTime);

    List<Map<String,Object>> getTrafficEventCountByCompanyIdGroupByTimeWindow(Long companyId, Date startTime,Date endTime);

    List<Map<String, Object>> getTemperatureByStationId(Long stationId, Date startTime, Date endTime);

    List<Map<String, Object>> getHumidityByStationId(Long stationId, Date startTime, Date endTime);

    List<Map<String, Object>> getPm_2_5ByStationId(Long stationId, Date startTime, Date endTime);

    List<Map<String, Object>> getEventCountByCompanyIdGroupByTrafficEventType(Long companyId);

    LogEventDetailVo getDetailById(Long eventDetailId);

    LogEventDetailVo getDetailWithAttachById(Long eventDetailId);
}

