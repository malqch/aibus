package com.wntime.modules.monitor.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 79448
 * @date 2020/10/30 15:35
 */
@Setter
@Getter
public class LocationAlarmVo {
    private List<AlarmVo> alarms;
}
