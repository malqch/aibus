package com.wntime.modules.officer.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wntime.modules.officer.entity.GuardianEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.Transient;

@Data
public class GuardianFrom extends BasicFrom {
    /**
     * @desc 学生id
     */
    private Long studentId;
    /**
     * @desc 顺位
     */
    private Integer seqNumber;
    /**
     * @desc 与学生关系
     */
    private String relationStudent;
    /**
     * @desc 手机号
     */
    private String mobileNumber;
    /**
     * @desc 登陆账号id
     */
    private Long loginUserId;

    private Long id;
    /**
     * @desc 基础信息id
     */

    private Long basicId;

    @ApiIgnore
    @Transient // 一般情况不序列化
    @JsonIgnore // jackson不序列化
    @JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true)
    public GuardianEntity getGuardian() {
        GuardianEntity entity = new GuardianEntity();
        entity.setId(id);
        entity.setBasicId(basicId);
        entity.setLoginUserId(loginUserId);
        entity.setMobileNumber(mobileNumber);
        entity.setRelationStudent(relationStudent);
        entity.setSeqNumber(seqNumber);
        entity.setStudentId(studentId);
        return entity;
    }
}
