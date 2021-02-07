package com.wntime.customer.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * @author 79448
 * @date 2020/10/29 15:22
 */
@Setter
@Getter
@Region("bus_drive_station_info")
public class BusDriveStationInfo{
    @Id
    private Long busId;

    private Long companyLineId;

    private Long stationId;

    /**
     * 途经站点状态  0 在当前站 1 驶离当前站
     */
    private int stationStatus;
}
