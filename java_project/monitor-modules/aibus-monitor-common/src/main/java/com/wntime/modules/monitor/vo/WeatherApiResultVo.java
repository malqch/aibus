package com.wntime.modules.monitor.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 79448
 * @date 2020/10/30 11:39
 */
@Setter
@Getter
public class WeatherApiResultVo {

    private List<LocationWeatherVo> results;

    public WeatherVo getWeather(){
        if(results!=null && !results.isEmpty()){
            return results.get(0).getNow();
        }
        return null;
    }
}
