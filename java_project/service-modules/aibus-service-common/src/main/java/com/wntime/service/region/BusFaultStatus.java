package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * 记录公交车CAN总线告警数据有效期为3分钟，是边缘计算获取CAN总线周期的3倍
 * @author 79448
 * @date 2020/9/16 10:13
 */
@Setter
@Getter
@Region("bus_fault_status")
public class BusFaultStatus {
    @Id
    private Long busId;
    private long companyId;

    public BusFaultStatus() {
    }

    public BusFaultStatus(Long busId) {
        this.busId = busId;
    }
}
