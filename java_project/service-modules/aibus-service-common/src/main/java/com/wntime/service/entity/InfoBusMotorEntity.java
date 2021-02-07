package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 电机表
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_bus_motor")
public class InfoBusMotorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "bus_motor_id", type = IdType.ID_WORKER)
    private Long busMotorId;
    /**
     * @desc 公交车Id
     */
    @NotNull(message = "所属公交车不能为空")
    private Long busId;

    //公交车
    @TableField(exist = false)
    private String busName;
    /**
     * @desc 电机型号Id
     */
    @NotNull(message = "电机型号不能为空")
    private Long motorTypeId;

    //电机类型名称
    @TableField(exist = false)
    private String motorTypeName;

    //公交车牌
    @TableField(exist = false)
    private String plateCode;

    //公交VIN
    @TableField(exist = false)
    private String vinCode;

    //公交车型
    @TableField(exist = false)
    private String busTypeName;

    /**
     * @desc 电机编号
     */
    @NotBlank(message = "电机编号不能为空")
    @Size(min = 0,max = 64,message = "电机编号长度应小于64")
    private String busMotorCode;
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
