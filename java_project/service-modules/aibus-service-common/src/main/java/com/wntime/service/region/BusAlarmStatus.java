package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * @author 79448
 * @date 2020/9/16 16:39
 */
@Setter
@Getter
@Region("bus_alarm_status")
public class BusAlarmStatus {
    @Id
    private Long busId;

    public BusAlarmStatus() {
    }

    public BusAlarmStatus(Long busId) {
        this.busId = busId;
    }
}
