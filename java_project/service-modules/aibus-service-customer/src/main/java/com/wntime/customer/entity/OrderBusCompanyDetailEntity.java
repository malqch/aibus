package com.wntime.customer.entity;

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
 * @desc 订单详情表
 * 
 * @date 2020-08-25 14:04:05
 */
@Data
@TableName("order_bus_company_detail")
public class OrderBusCompanyDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "order_detail_id", type = IdType.ID_WORKER)
	private Long orderDetailId;
	/**
	 * @desc 订单Id
	 */
	@NotNull(message = "所属订单不能为空")
	private Long orderId;
	/**
	 * @desc 公交车型Id
	 */
	@NotNull(message = "公交车型不能为空")
	private Long busTypeId;

	//公交车型名称
	@TableField(exist = false)
	private String busTypeName;

	/**
	 * @desc 订单数量
	 */
	@NotNull(message = "订单数量不能为空")
	@Positive(message = "订单数量要大于0")
	private Integer orderDetailNum;
	/**
	 * @desc 说明
	 */
	@Size(min = 0,max = 200,message = "订单详情长度应小200")
	private String orderDetailDesc;
	/**
	 * @desc 是否删除
	 */
	private Integer isDeleted;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;

}
