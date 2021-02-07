package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 电机类型表
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_motor_type")
public class InfoMotorTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "motor_type_id", type = IdType.ID_WORKER)
    private Long motorTypeId;
    /**
     * @desc 设备类型Id
     */
    @NotNull(message = "设备类型不能为空")
    private Long deviceTypeId;

    // 设备类型名称
    @TableField(exist = false)
    private String deviceTypeName;

    /**
     * @desc 电机型号
     */
    @NotNull(message = "电机型号不能为空")
    @Size(min=0,max = 32,message = "电机型号不能超过32个字符")
    private String motorTypeName;
    /**
     * @desc 总功率 kW
     */
    @NotNull(message = "总功率不能为空")
    @Positive(message = "总功率必须大于0" )
    private Double motorTypePower;
    /**
     * @desc 总扭矩 N.m
     */
    @NotNull(message = "总扭矩不能为空")
    @Positive(message = "总扭矩必须大于0" )
    private Double motorTypeTorque;
    /**
     * @desc 是否删除
     */
    private Integer isDeleted;
    /**
     * @desc 是否启用
     */
    private Integer isEnabled;
    /**
     * @desc 创建人
     */
    private Long createdBy;
    /**
     * @desc 创建时间
     */
    private Timestamp createdDate;
    /**
     * @desc 修改人
     */
    private Long modifiedBy;
    /**
     * @desc 修改时间
     */
    private Timestamp modifiedDate;

    @TableField(exist = false)
    private String createUserName;

    @TableField(exist = false)
    private String modifiedUserName;

}
