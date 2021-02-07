package com.wntime.service.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ysc
 * 2020/8/29 9:05
 */
@Data
public class AfterSalesBusInfoVO {
    private Long busId;
    private Long busTypeId;
    private String busTypeName;
    private String plateCode;
    private String vinCode;

    private Long factoryId;

    private Long companyId;
    private String companyName;
    private String companyAddress;
    private String companyPerson;
    private String companyPhone;
    private String companyEmail;

    private Long companyLineId;
    private String companyLineName;

    private Long busStatusId;
    private String busStatusCode;
    private String busStatusName;


}
