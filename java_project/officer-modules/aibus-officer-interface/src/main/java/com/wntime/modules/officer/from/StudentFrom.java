package com.wntime.modules.officer.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wntime.modules.officer.entity.StudentEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.Transient;

@Data
public class StudentFrom extends BasicFrom {
    /**
     * @desc 第一监护人id
     */
    private Long guardianId;
    /**
     * @desc 班级id
     */
    private Long classesId;
    /**
     * @desc 学校id
     */
    private Long schoolId;

    /**
     * @desc 基础信息id
     */
    private Long basicId;

    /**
     * @desc 主键
     */

    private Long id;
    @ApiIgnore
    @Transient // 一般情况不序列化
    @JsonIgnore // jackson不序列化
    @JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true)
    public StudentEntity getStudent() {
        StudentEntity entity = new StudentEntity();
        entity.setGuardianId(guardianId);
        entity.setClassesId(classesId);
        entity.setSchoolId(schoolId);
        return entity;
    }
}
