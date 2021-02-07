package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 设备日志表
 * 
 * @date 2020-08-25 16:36:18
 */
@Data
@TableName("log_bus_device")
public class LogBusDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "log_device_id", type = IdType.ID_WORKER)
	private Long logDeviceId;
	/**
	 * @desc 设备Id
	 */
	private Long busDeviceId;

	//设备名称
	@TableField(exist = false)
	private String deviceName;
	/**
	 * @desc 状态说明
	 */
	private String deviceDesc;
	/**
	 * @desc 设备状态
    @depr
	 */
	private Integer deviceStatus;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;

	@TableField(exist = false)
	private String vinCode;

	@TableField(exist = false)
	private String plateCode;

	@TableField(exist = false)
	private String busCode;

}
