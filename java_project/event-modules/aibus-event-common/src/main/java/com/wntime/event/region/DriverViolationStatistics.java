package com.wntime.event.region;

import com.wntime.event.vo.DriverViolationEvent;
import com.wntime.modules.gemfire.key.BusDateKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 79448
 * @date 2020/8/25 19:02
 */
@Setter
@Getter
@Region("driver_violation_statistics")
public class DriverViolationStatistics {

    @Id
    private BusDateKey key;


    private Set<DriverViolationEvent> events;


    public DriverViolationStatistics(Long busId) {
        this.key = new BusDateKey(busId);
        this.events=new HashSet<>();
    }

    public DriverViolationStatistics(BusDateKey key){
        this.key=key;
        this.events=new HashSet<>();
    }
    public DriverViolationStatistics() {
    }
}
