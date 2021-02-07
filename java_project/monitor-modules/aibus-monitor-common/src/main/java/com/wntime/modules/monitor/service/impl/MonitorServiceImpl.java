package com.wntime.modules.monitor.service.impl;

import com.wntime.modules.monitor.dao.LogWeatherWarnMapper;
import com.wntime.modules.monitor.entity.LogWeatherWarn;
import com.wntime.modules.monitor.service.MonitorService;
import com.wntime.modules.monitor.vo.*;
import com.wntime.service.common.service.ConfigParamService;
import com.wntime.service.common.vo.AreaVo;
import com.wntime.service.common.vo.ConfigParamVo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 79448
 * @date 2020/11/2 14:01
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Resource
    private ConfigParamService configParamService;
    @Resource
    private LogWeatherWarnMapper logWeatherWarnMapper;
    @Resource
    private RestTemplate restTemplate;

    @Override
    @Cacheable(value = "weather_real",key = "#area.areaId",unless = "#result.text==null")
    public WeatherVo getWeather(AreaVo area){
        AtomicReference<WeatherVo> result = new AtomicReference();
        Optional<ConfigParamVo> config = configParamService.queryConfigByGroupCode("weather", "real");
        config.ifPresent(configParamVo -> {
            try {
                WeatherApiResultVo res = restTemplate.getForObject(configParamVo.getParamChar() + area.getAreaName(), WeatherApiResultVo.class);
                result.set(res.getWeather());
            } catch (Exception e) {
                result.set(new WeatherVo());
            }
        });
        return result.get();
    }

    @Override
    @Cacheable(value = "weather_alarm", key = "#area.areaId", unless = "#result.size()==0")
    public List<AlarmVo> getAlarm(AreaVo area){
        AtomicReference<List<AlarmVo>> result = new AtomicReference();
        Optional<ConfigParamVo> config = configParamService.queryConfigByGroupCode("weather", "alarm");
        config.ifPresent(configParamVo -> {
            try {
                AlarmApiResultVo res = restTemplate.getForObject(configParamVo.getParamChar() + area.getAreaName(), AlarmApiResultVo.class);
                List<AlarmVo> alarms = res.getAlarm();
                if(alarms!=null && !alarms.isEmpty()){
                    for(AlarmVo alarm :alarms){
                        LogWeatherWarn warn=new LogWeatherWarn();
                        warn.setTitle(alarm.getTitle());
                        warn.setAreaId(area.getAreaId());
                        warn.setWarnContent(alarm.getDescription());
                        warn.setCreatedBy(1L);
                        warn.setCreatedDate(new Date());
                        logWeatherWarnMapper.insert(warn);
                    }
                    result.set(alarms);
                }else {
                   result.set(logWeatherWarnMapper.queryLatelyAlarm(area.getAreaId()));
                }
            } catch (Exception e) {
                result.set(Collections.emptyList());
            }
        });
        return result.get();
    }
    @Override
    @Cacheable(value = "weather_future", key = "#area.areaId", unless = "#result.size()==0")
    public List<FutureWeatherVo> getFutureWeather(AreaVo area){
        AtomicReference<List<FutureWeatherVo>> result = new AtomicReference();
        Optional<ConfigParamVo> config = configParamService.queryConfigByGroupCode("weather", "future");
        config.ifPresent(configParamVo -> {
            try {
                FutureWeatherApiResultVo res = restTemplate.getForObject(configParamVo.getParamChar() + area.getAreaName(), FutureWeatherApiResultVo.class);
                result.set(res.getWeathers());
            } catch (Exception e) {
                result.set(Collections.emptyList());
            }
        });
        return result.get();
    }

}
