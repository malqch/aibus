package com.wntime.event.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 *司机相关违规事件
 * @author 79448
 * @date 2020/8/25 18:22
 */
@Setter
@Getter
@Region("driver_violation")
public class DriverViolation {

    @Id
    private Long id;

    private Long busId;

    private long eventTargetId;

    private String eventTargetName;

    private String eventTargetCode;

}
