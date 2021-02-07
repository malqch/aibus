package com.wntime.ec.module.schedule.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wing
 * @create 2020/8/29 15:30
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlleyesDeviceQryRspVo {
    String device_code;
    String device_desc_code;
    Integer device_status;
}


