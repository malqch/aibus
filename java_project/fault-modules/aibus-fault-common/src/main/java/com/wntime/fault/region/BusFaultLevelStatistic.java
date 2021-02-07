package com.wntime.fault.region;

import com.wntime.modules.gemfire.key.BusDateKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * 公交故障级别统计信息
 * @author 79448
 * @date 2020/8/27 20:15
 */
@Setter
@Getter
@Region("bus_fault_level_statistic")
public class BusFaultLevelStatistic {

    @Id
    private BusDateKey key;

    private List<FaultInfo> faultLevelInfos;

    public BusFaultLevelStatistic() {
    }

    public BusFaultLevelStatistic(Long busId) {
        this.key = new BusDateKey(busId) ;
        this.faultLevelInfos=new ArrayList<>();
    }

    public BusFaultLevelStatistic(BusDateKey key) {
        this.key = key;
        this.faultLevelInfos=new ArrayList<>();
    }
}
