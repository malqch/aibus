package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @desc 公交订单表
 * 
 * @date 2020-08-25 14:04:05
 */
@Data
@TableName("order_bus_company")
public class OrderBusCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "order_id", type = IdType.ID_WORKER)
	private Long orderId;
	/**
	 * @desc 订单编码
	 */
	@NotBlank(message = "订单编码不能为空")
	@Size(min = 0,max = 20,message = "订单编码长度应小于20")
	private String orderCode;

	private Integer isCompleted;
	/**
	 * @desc 车厂Id
	 */
	@NotNull(message = "公交车厂不能为空")
	private Long factoryId;
	/**
	 * @desc 公交公司Id
	 */
	@NotNull(message = "公交公司不能为空")
	private Long companyId;
	/**
	 * @desc 销售人员Id
	 */
	private Long sellerId;
	/**
	 * @desc 签订时间
	 */
	@NotNull(message = "签订时间不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Timestamp orderDate;
	/**
	 * @desc 是否删除
	 */
	private Integer isDeleted;
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
