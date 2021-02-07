package com.wntime.service.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 行驶日志表
 * 
 * @date 2020-08-25 16:36:18
 */
@Data
@TableName("log_bus_drive")
public class LogBusDriveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "bus_drive_id", type = IdType.ID_WORKER)
	private Long busDriveId;
	/**
	 * @desc 行程Id
	 */
	private Long itineraryId;

	/**
	 * @desc 车辆Id
	 */
	@TableField(exist = false)
	private Long busId;

	/**
	 * 车牌
	 */
	@TableField(exist = false)
	private String plateCode;

	/**
	 * vin码
	 */
	@TableField(exist = false)
	private String vinCode;

	@TableField(exist = false)
	private String busCode;
	/**
	 * @desc 车速 km/h
	 */
	private Double busSpeed;
	/**
	 * @desc 总里程 km
	 */
	private Double busTotalMile;

	@TableField(exist = false)
	private Double surplusMile;
	/**
	 * @desc 经度
	 */
	@JsonProperty("factoryLongitude")
	private Double busLongitude;
	/**
	 * @desc 纬度
	 */
	@JsonProperty("factoryLatitude")
	private Double busLatitude;
	/**
	 * @depr  车辆状态
    @value 配置参数
	 */
	private Long busStatus;

	@TableField(exist = false)
	private String busStatusShow;
	/**
	 * @desc 电池电压
	 */
	private Double busBatteryVoltage;
	/**
	 * @desc 电池能量
	 */
	private Double busBatteryEnergy;
	/**
	 * @desc 电池电流
	 */
	private Double busBatteryCurrent;
	/**
	 * @desc 电池温度
	 */
	private Double busBatteryTemperature;
	/**
	 * @desc 电池状态
    @value 配置参数
	 */
	private Long busBatteryStatus;

	@TableField(exist = false)
	private String busBatteryShow;
	/**
	 * @desc 电机状态
    @depr
	 */
	private Long busMotorStatus;

	@TableField(exist = false)
	private String busMotorShow;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;


}
