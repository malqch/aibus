package com.wntime.fault.region;

import com.wntime.fault.vo.FaultInfoItem;
import com.wntime.modules.gemfire.key.BusDateKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author 79448
 * @date 2020/9/1 9:30
 */
@Setter
@Getter
@Region("bus_fault_info")
public class BusFaultInfo {

    @Id
    private BusDateKey key;

    private Set<FaultInfoItem> faults;

    public BusFaultInfo() {
    }

    public BusFaultInfo(Long busId) {
        this.key= new BusDateKey(busId);
        this.faults=new TreeSet<>();
    }

    public BusFaultInfo(BusDateKey key) {
        this.key = key;
        this.faults=new TreeSet<>();
    }
}
