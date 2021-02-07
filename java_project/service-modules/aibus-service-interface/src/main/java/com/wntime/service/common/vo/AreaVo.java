package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/5 15:19
 */
@Setter
@Getter
public class AreaVo {
    private long areaId;
    private String areaName;

    public AreaVo(long areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }
}
