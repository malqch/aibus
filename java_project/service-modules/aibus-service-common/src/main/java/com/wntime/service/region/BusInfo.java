package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 *
 * @author 79448
 * @date 2020/8/26 9:48
 */
@Setter
@Getter
@Region("bus_info")
public class BusInfo {

    @Id
    private Long busId;

    private String plateCode;

    private String vinCode;

    private Long factoryId;

    private Long companyId;
    /**
     * 荷载人数
     */
    private int peopleLoadCount;
    /**
     * 车辆状态，0 正常  1 告警（事件里的告警）  2 故障 （故障里的告警）
     */
    private int status;
}
