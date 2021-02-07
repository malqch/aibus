package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/27 16:20
 */
@Setter
@Getter
public class BusLineVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private long companyId;
    private String companyName;
    @JsonSerialize(using = LongToStringSerialize.class)
    private long companyLineId;
    private String companyLineCode;
    private String companyLineName;
    private String serviceTime;
    private String areaName;
    private Long areaId;
}
