package com.wntime.event.region;

import com.wntime.event.vo.AlarmInfoItem;
import com.wntime.modules.gemfire.key.BusDateKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author 79448
 * @date 2020/8/28 19:57
 */
@Setter
@Getter
@Region("bus_alarm_info")
public class BusAlarmInfo{

    @Id
    private BusDateKey key;

    private Set<AlarmInfoItem> alarms;

    public BusAlarmInfo() {
    }

    public BusAlarmInfo(Long busId ) {
        this.key = new BusDateKey(busId);
        this.alarms=new TreeSet<>();
    }

    public BusAlarmInfo(BusDateKey key) {
        this.key = key;
        this.alarms=new TreeSet<>();
    }
}
