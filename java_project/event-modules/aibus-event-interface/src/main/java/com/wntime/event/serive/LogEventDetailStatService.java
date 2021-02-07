package com.wntime.event.serive;

import com.wntime.event.vo.EventEnvSearchApiVo;
import com.wntime.event.vo.EventHealthSearchApiVo;
import com.wntime.event.vo.EventTrafficSearchApiVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ysc
 * 2020/8/28 13:23
 */

public interface LogEventDetailStatService {

    List<Map<String,Object>> getTrafficEventCountByCompanyIdGroupByBus(Long companyId, Date startTime);

    List<Map<String,Object>> getTrafficEventCountByCompanyIdGroupByTimeWindow(Long companyId, Date startTime,Date endTime);

    List<Map<String, Object>> getTemperatureByStationId(Long stationId, Date startTime, Date endTime);

    List<Map<String, Object>> getHumidityByStationId(Long stationId, Date startTime, Date endTime);

    List<Map<String, Object>> getPm_2_5ByStationId(Long stationId, Date startTime, Date endTime);

    List<Map<String, Object>> getEventCountByCompanyIdGroupByTrafficEventType(Long companyId);

    /*
    查询公共环境信息
     */
    List<EventEnvSearchApiVo> getEnvSearchData(Map<String, Object> params);

    /*
    查询共同违章信息
     */
    List<EventTrafficSearchApiVo> getTrafficSearchData(Map<String, Object> params);

    /*
    查询公共卫生信息
     */
    List<EventHealthSearchApiVo> getHealthSearchData(Map<String, Object> params);
}
