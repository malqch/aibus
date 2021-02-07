package com.wntime.modules.officer.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wntime.modules.officer.entity.AuthorizeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.Transient;

@Data
public class AuthorizeFrom extends BasicFrom {
    /**
     * @desc 主键
     */
    @TableId
    private Long id;
    /**
     * @desc 基本信息主键
     */
    private Long basicId;
    /**
     * @desc 学生id
     */
    private Long studentId;
    /**
     * @desc 授权状态
     */
    private String authStatus;
    /**
     * @desc 授权序号
     */
    private Integer authNumber;
    /**
     * @desc 与学生关系
     */
    private String relationStudent;
    /**
     * @desc 手机号
     */
    private String mobileNumber;
    @ApiIgnore
    @Transient // 一般情况不序列化
    @JsonIgnore // jackson不序列化
    @JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true)
    public AuthorizeEntity getAuthorize() {
        AuthorizeEntity entity = new AuthorizeEntity();
        entity.setId(id);
        entity.setId(basicId);
        entity.setStudentId(studentId);
        entity.setAuthStatus(authStatus);
        entity.setAuthNumber(authNumber);
        entity.setRelationStudent(relationStudent);
        return entity;
    }
}
