package com.wntime.modules.officer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DriverInfoDto {

    private Long id;
    private Long basicId;
    private String drivingLicenseNo;
    private Date firstIssueDate;
    private String drivingLicenseClass;
    private String mobileNumber;
    private String driversLicensePhoto;
    private Date validPeriodStart;
    private Date validPeriodEnd;
    private String noCriminalRecordPhoto;
    private Long loginUserId;
    private String loginName;
    private String isPrimary;
    private Long createUserId;
    private String createDt;
    private Long modifyUserId;
    private Date modifyDt;
    private String isDeleted;
    private String idNo;
    private String fullName;
    private String sex;
    private Integer age;
    private String takePhoto;
    private String residentialAddress;
    private String category;
    private String createUserName;
    private String modifiedUserName;
}
