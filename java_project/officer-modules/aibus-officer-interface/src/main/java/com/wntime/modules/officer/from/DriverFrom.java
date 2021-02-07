package com.wntime.modules.officer.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wntime.modules.officer.entity.DriverEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.Transient;
import java.util.Date;

@Data
public class DriverFrom extends BasicFrom {


    /**
     * 主键
     */

    private Long id;

    /**
     * 基本信息主键
     */
    private Long basicId;

    /**
     * 驾驶证件号
     */
    private String drivingLicenseNo;
    /**
     * 初次领证时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date firstIssueDate;
    /**
     * 驾驶证类型
     */
    private String drivingLicenseClass;
    /**
     * 手机号
     */
    private String mobileNumber;
    /**
     * 驾驶证照片
     */
    private String driversLicensePhoto;
    /**
     * 驾驶证有效期限起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date validPeriodStart;
    /**
     * 驾驶证有效期限结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date validPeriodEnd;
    //    is_primary
    private String isPrimary;
    /**
     * 无犯罪记录证明照片
     */
    private String noCriminalRecordPhoto;

    @ApiIgnore
    @Transient // 一般情况不序列化
    @JsonIgnore // jackson不序列化
    @JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true)
    public DriverEntity getDriver() {
        DriverEntity entity = new DriverEntity();
        entity.setId(id);
        entity.setBasicId(basicId);
        entity.setDriversLicensePhoto(driversLicensePhoto);
        entity.setDrivingLicenseClass(drivingLicenseClass);
        entity.setDrivingLicenseNo(drivingLicenseNo);
        entity.setFirstIssueDate(firstIssueDate);
        entity.setMobileNumber(mobileNumber);
        entity.setIsPrimary(isPrimary);
        entity.setNoCriminalRecordPhoto(noCriminalRecordPhoto);
        entity.setValidPeriodEnd(validPeriodEnd);
        entity.setValidPeriodStart(validPeriodStart);
        return entity;
    }

}
