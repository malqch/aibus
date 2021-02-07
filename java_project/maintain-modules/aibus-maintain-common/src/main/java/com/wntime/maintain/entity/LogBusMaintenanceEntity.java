package com.wntime.maintain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 维保日志表
 * 
 * @date 2020-08-25 14:00:24
 */
@Data
@TableName("log_bus_maintenance")
public class LogBusMaintenanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "maintenance_id", type = IdType.ID_WORKER)
	private Long maintenanceId;
	/**
	 * @desc 公交车Id
	 */
	private Long busId;
	/**
	 * @desc 公交线路Id
	 */
	private Long companyLineId;
	/**
	 * @desc 维保内容
	 */
	private String maintenanceDesc;

	/**
	 * 公交车状态
	 */
	private Long busStatus;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;

}
