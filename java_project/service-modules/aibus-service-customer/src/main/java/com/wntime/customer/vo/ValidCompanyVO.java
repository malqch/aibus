package com.wntime.customer.vo;

import lombok.Data;

/**
 * @author ysc
 * 2020/9/3 14:31
 */

@Data
public class ValidCompanyVO {

    private Long companyId;
    private String companyName;
    private Long areaId;
    private String areaName;
    private Long parentAreaId;
    private String parentAreaName;
}
