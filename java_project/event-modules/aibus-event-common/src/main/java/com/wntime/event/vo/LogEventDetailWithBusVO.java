package com.wntime.event.vo;

import com.wntime.event.entity.LogEventDetailEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/8/27 15:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogEventDetailWithBusVO extends LogEventDetailEntity {

    private String vinCode;
    private String plateCode;
}
