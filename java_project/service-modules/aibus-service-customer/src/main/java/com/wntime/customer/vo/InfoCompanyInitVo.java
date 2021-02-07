package com.wntime.customer.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class InfoCompanyInitVo {
    private long busStationId;

    private String busStationName;

    private long busId;

    private String vinCode;

    private long companyLineId;

    private String companyLineName;

    private long companyId;

    private String companyName;

    private long areaId;

    private String areaName;

    private long parentAreaId;

    private String parentAreaName;

    public InfoCompanyInitVo() {
    }
}
