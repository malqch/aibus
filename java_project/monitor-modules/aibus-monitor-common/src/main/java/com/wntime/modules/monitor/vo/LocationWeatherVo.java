package com.wntime.modules.monitor.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 79448
 * @date 2020/11/2 15:19
 */
@Setter
@Getter
public class LocationWeatherVo {

    private WeatherVo now;

    private List<FutureWeatherVo> daily;
}
