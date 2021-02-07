package com.wntime.modules.officer.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.Transient;

@Data
public class SafetyOfficerFrom extends BasicFrom {
    /**
     * @desc 无犯罪记录证明照片
     */
    private String noCriminalRecordPhoto;
    /**
     * @desc 手机号
     */
    private String mobileNumber;
    /**
     * 系统登录账号id
     */
    private Long loginUserId;
    /**
     * @desc 基础信息id
     */
    private Long basicId;

    private String isPrimary;
    /**
     * @desc 主键
     */
    private Long id;


    @ApiIgnore
    @Transient // 一般情况不序列化
    @JsonIgnore // jackson不序列化
    @JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true)
    public SafetyOfficerEntity getSafetyOfficer() {
        SafetyOfficerEntity entity = new SafetyOfficerEntity();
        entity.setId(id);
        entity.setBasicId(basicId);
        entity.setIsPrimary(isPrimary);
        entity.setMobileNumber(mobileNumber);
        entity.setNoCriminalRecordPhoto(noCriminalRecordPhoto);
        entity.setLoginUserId(loginUserId);
        return entity;
    }
}
