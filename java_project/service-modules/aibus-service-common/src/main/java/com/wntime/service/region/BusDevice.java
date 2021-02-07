package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Date;

/**
 * @author 79448
 * @date 2020/8/29 16:32
 */
@Setter
@Getter
@Region("bus_device")
public class BusDevice {

    @Id
    private BusDeviceKey key;

    //private Long busDeviceId;

    private Long deviceTypeId;
    /**
     * @desc 公交车Id
     */
    private Long busId;
    /**
     * @desc 设备名称
     */
    private String deviceName;
    /**
     * @desc 设备编号
     */
    private String deviceCode;
    /**
     * @desc 设备状态
     @value 连接中，在线，离线
     */
    private Integer deviceStatus;

    /**
     * 最后更新状态时间
     */
    private Date updateTime;
}
