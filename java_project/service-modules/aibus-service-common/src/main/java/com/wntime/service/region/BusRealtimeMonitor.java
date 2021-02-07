package com.wntime.service.region;

import com.wntime.modules.gemfire.key.BusDateKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 车辆实时监控数据
 * @author 79448
 * @date 2020/8/26 17:00
 */
@Setter
@Getter
@Region("bus_realtime_monitor")
public class BusRealtimeMonitor {
    @Id
    private BusDateKey key;

    private Double busBatteryEnergy;

    private int busMotorStatus;

    private Double busSpeed;

    private Double busTotalMile;

    private Double surplusMile;

    private Double factoryLongitude;

    private Double factoryLatitude;

    private int busStatus;

    private Double busBatteryVoltage;

    private Double busBatteryCurrent;

    private Double busBatteryTemperature;

    private int busBatteryStatus;

    public BusRealtimeMonitor() {
    }

    public BusRealtimeMonitor(Long busId) {
        this.key =new BusDateKey(busId);
    }
}
