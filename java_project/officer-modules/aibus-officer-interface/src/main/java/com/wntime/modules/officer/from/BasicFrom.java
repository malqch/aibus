package com.wntime.modules.officer.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.Transient;

@Data
public class BasicFrom {


    /**
     * @desc 身份证号
     */
    private String idNo;
    /**
     * @desc 姓名
     */
    private String fullName;
    /**
     * @desc 性别
     */
    private String sex;
    /**
     * @desc 年龄
     */
    private Integer age;
    /**
     * @desc 身份验证照片
     */
    private String takePhoto;
    /**
     * @desc 居住地址
     */
    private String residentialAddress;

    @ApiIgnore
    @Transient // 一般情况不序列化
    @JsonIgnore // jackson不序列化
    @JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true)
    public PeopleBasicFactsEntity getPeopleBasicFacts() {
        PeopleBasicFactsEntity p
                = new PeopleBasicFactsEntity();
        p.setIdNo(idNo);
        p.setAge(age);
        p.setFullName(fullName);
        p.setSex(sex);
        p.setTakePhoto(takePhoto);
        p.setResidentialAddress(residentialAddress);
        return p;
    }
}
