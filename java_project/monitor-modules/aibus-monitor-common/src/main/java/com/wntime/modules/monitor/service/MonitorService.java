package com.wntime.modules.monitor.service;

import com.wntime.modules.monitor.vo.AlarmVo;
import com.wntime.modules.monitor.vo.FutureWeatherVo;
import com.wntime.modules.monitor.vo.WeatherVo;
import com.wntime.service.common.vo.AreaVo;

import java.util.List;

public interface MonitorService {


    WeatherVo getWeather(AreaVo area);

    List<AlarmVo> getAlarm(AreaVo area);

    List<FutureWeatherVo> getFutureWeather(AreaVo area);
}
