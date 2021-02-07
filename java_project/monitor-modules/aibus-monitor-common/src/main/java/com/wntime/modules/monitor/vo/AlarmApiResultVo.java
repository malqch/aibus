package com.wntime.modules.monitor.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author 79448
 * @date 2020/10/30 15:34
 */
@Setter
@Getter
public class AlarmApiResultVo {

    private List<LocationAlarmVo> results;

    public List<AlarmVo> getAlarm(){
        if(results!=null && !results.isEmpty()){
            LocationAlarmVo result = results.get(0);
          return result.getAlarms();

        }
        return Collections.emptyList();
    }
}
