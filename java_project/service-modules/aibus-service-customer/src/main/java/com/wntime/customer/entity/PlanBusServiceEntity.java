package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 营运计划表
 * 
 * @date 2020-08-25 14:00:24
 */
@Data
@TableName("plan_bus_service")
public class PlanBusServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "plan_service_id", type = IdType.ID_WORKER)
	private Long planServiceId;
	/**
	 * @desc 公交车Id
	 */
	private Long busId;

	@TableField(exist = false)
	private String plateCode;

	@TableField(exist = false)
	private String busCode;

	@TableField(exist = false)
	private String vinCode;

	@TableField(exist = false)
	private String direction;

	/**
	 * @desc 公交线路Id
	 */
	private Long companyLineId;
	/**
	 * @desc 起始时间
	 */
	private Timestamp beginDate;
	/**
	 * @desc 截止时间
	 */
	private Timestamp endDate;
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

	private Long driverId;
	private Long safetyOfficerId;

}
