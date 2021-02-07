package com.wntime.service.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 电机类型表
 * @date 2020-08-25 14:28:17
 */
@Data
public class BusInfoMotorTypeVo  {
    /**
     * @desc 主键
     */
    private Long motorTypeId;
    /**
     * @desc 设备类型Id
     */
    private Long deviceTypeId;

    // 设备类型名称
    private String deviceTypeName;

    /**
     * @desc 电机型号
     */
    private String motorTypeName;
    /**
     * @desc 总功率 kW
     */
    private Double motorTypePower;
    /**
     * @desc 总扭矩 N.m
     */
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
}
