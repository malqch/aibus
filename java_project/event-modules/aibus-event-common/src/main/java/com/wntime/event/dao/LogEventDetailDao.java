package com.wntime.event.dao;

import com.wntime.event.entity.LogEventDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.event.vo.*;
import com.wntime.service.common.vo.StatisticsResultVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc 事件日志表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Mapper
public interface LogEventDetailDao extends BaseMapper<LogEventDetailEntity> {

    // 查询公共环境信息
    List<EventEnvSearchApiVo> getEnvSearchData(Map<String,Object> params);
    // 查询公共交通违章信息
    List<EventTrafficSearchApiVo> getTrafficSearchData(Map<String,Object> params);
    // 查询公共卫生信息
    List<EventHealthSearchApiVo> getHealthSearchData(Map<String,Object> params);

    List<EventHealthTempStatVo> getTempStatByCompanyAndLinePage(Map<String,Object> params);

    List<EventTrafficStatVo> getTrafficStatByCompanyAndLinePage(Map<String,Object> params);

    List<StatisticsResultVo> getTempDetailByEventDetailId(@Param(value = "eventDetailIdList") List<Long> eventDetailIdList);

    List<Map<String, Object>> getEventCountByEventTypeAndCompanyIdGroupByBus(String eventTypeCode,Long companyId, Date startTime);

    List<Map<String, Object>> getEventCountByEventTypeAndCompanyIdGroupByTimeWindow(String eventTypeCode,Long companyId, Date startTime,Date endTime);

    List<Map<String, Object>> getEnvMetricDataPointsByLogEventDetailIdList(List<Long> logEventDetailIdList,String subEventTargetCode);

    List<Long> getLogEventDetailIdListByStationId(Long busStationId,Date startTime,Date endTime);

    List<Map<String, Object>> getEventCountByCompanyIdGroupByTrafficEventType(Long companyId);

    List<LogEventDetailVo> listDetail(Map<String,Object> params);

    LogEventDetailVo getDetail(Long eventDetailId);

     String getParamConfigStationName();

     List<EventCountVo> queryEventTargetGroupCount(@Param("busId")long busId, @Param("eventTargetCodes")Set<String> eventTargetCodes);

     List<EventCountVo> queryEventTypeGroupCount(@Param("busId")long busId, @Param("eventTypeCodes")Set<String> eventTypeCodes);

     List<EventDetailVo> queryEventLogListByTypeCode(@Param("busId")long busId, @Param("eventTypeCodes")Set<String> eventTypeCodes);
}
