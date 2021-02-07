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
 * @desc 电池类型表
 * 
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_battery_type")
public class InfoBatteryTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "battery_type_id", type = IdType.ID_WORKER)
	private Long batteryTypeId;
	/**
	 * @desc 设备类型Id
	 */
	@NotNull(message = "设备类型不能为空")
	private Long deviceTypeId;

	//设备类型名称
	@TableField(exist = false)
	private String deviceTypeName;
	/**
	 * @desc 设备名称
	 */
	@NotNull(message = "电池型号不能为空")
	@Size(min=0,max = 32,message = "设备类型不能超过32个字符")
	private String batteryTypeName;
	/**
	 * @desc 电池电压
	 */
	@NotNull(message = "电池电压不能为空")
	@Positive(message = "电池电压必须大于0" )
	private Double batteryTypeVoltage;
	/**
	 * @desc 电池能量  2000mA
	 */
	@NotNull(message = "电池能量不能为空")
	@Positive(message = "电池能量必须大于0" )
	private Double batteryTypeEnergy;
	/**
	 * @desc 电池电流
	 */
	@NotNull(message = "电池电流不能为空")
	@Positive(message = "电池电流必须大于0" )
	private Double batteryTypeCurrent;
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
