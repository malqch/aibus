package com.wntime.modules.monitor.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author 79448
 * @date 2020/11/2 16:28
 */
@Setter
@Getter
public class FutureWeatherApiResultVo {

    private List<LocationWeatherVo> results;

    public List<FutureWeatherVo> getWeathers(){
        if(results !=null && !results.isEmpty()){
            return results.get(0).getDaily();
        }
        return Collections.emptyList();
    }

}
