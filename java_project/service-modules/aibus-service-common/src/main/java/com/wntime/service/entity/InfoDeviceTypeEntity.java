package com.wntime.service.entity;

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
 * @desc 设备类型
 * 
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_device_type")
public class InfoDeviceTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "device_type_id", type = IdType.ID_WORKER)
	private Long deviceTypeId;
	/**
	 * @desc 设备类型名称
	 */
	@NotNull(message = "设备类型不能为空")
	@Size(min=0,max = 20,message = "设备类型不能超过20个字符")
	private String deviceTypeName;
	/**
	 * @desc 设备类型编码
	 */
	@NotNull(message = "设备类型编码不能为空")
	@Size(min=0,max = 20,message = "设备类型编码不能超过20个字符")
	private String deviceTypeCode;
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
