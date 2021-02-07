package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
@Setter
@Getter
public class BusBaseInfoVo {

    private Long busId;

    private String plateCode;

    private String vinCode;

    private Long factoryId;

    private Long companyId;

    /**
     * 荷载人数
     */
    private int peopleLoadCount;


    private int status;
}
