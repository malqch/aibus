package com.wntime.fault.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 故障日志表
 * 
 * @date 2020-08-25 13:48:11
 */
@Data
@TableName("log_fault_detail")
public class LogFaultDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "fault_detail_id", type = IdType.ID_WORKER)
	private Long faultDetailId;
	/**
	 * @desc 故障采集Id
	 */
	private Long collectFaultId;

	/**
	 * @desc 采集内容
	 */
	@TableField(exist = false)
	private String collectFault;

	/**
	 * @desc 采集编码
	 */
	@TableField(exist = false)
	private String collectCode;

	/**
	 * @desc 公交车Id
	 */
	private Long busId;

	/**
	 * @desc VinCode
	 */
	@TableField(exist = false)
	private String vinCode;
	/**
	 * @desc 车牌号
	 */
	@TableField(exist = false)
	private String plateCode;
	/**
	 * @desc 车辆编号
	 */
	@TableField(exist = false)
	private String busCode;

	/**
	 * 行驶日志Id
	 */
	private Long busDriveId;
	/**
	 * @desc 故障类型Id
	 */
	private Long faultTypeId;
	@TableField(exist = false)
	private String faultTypeName;
	/**
	 * @desc 故障标签Id
	 */
	private Long faultTargetId;
	@TableField(exist = false)
	private String faultTargetName;
	/**
	 * @desc 故障级别Id
	 */
	private Long faultLevelId;
	@TableField(exist = false)
	private String faultLevelName;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
