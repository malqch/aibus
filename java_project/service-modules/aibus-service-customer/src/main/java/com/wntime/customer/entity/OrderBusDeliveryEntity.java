package com.wntime.customer.entity;

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
 * @desc 车辆交付表
 * 
 * @date 2020-08-25 14:00:24
 */
@Data
@TableName("order_bus_delivery")
public class OrderBusDeliveryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "bus_delivery_id", type = IdType.ID_WORKER)
	private Long busDeliveryId;
	/**
	 * @desc 公交车Id
	 */
	@NotNull(message = "公交车不能为空")
	private Long busId;

	@NotNull(message = "车牌不能为空")
	@Size(min = 0,max = 16,message = "车牌长度应小于16")
	private String plateCode;

	@Size(min = 0,max = 64,message = "车辆编号长度应小于64")
	private String busCode;
	/**
	 * @desc 订单交付Id
	 */
	@NotNull(message = "所属交付订单不能为空")
	private Long companyDeliveryId;

	@TableField(exist = false)
	private Long orderId;
	/**
	 * @desc 交付日期
	 */
	@NotNull(message = "交付日期不能为空")
	private Timestamp orderDeliveryDate;
	/**
	 * @desc 交付说明
	 */
	@Size(min = 0,max = 200,message = "交付说明长度应小于200")
	private String orderDeliveryDesc;
	/**
	 * @desc 出保日期
	 */
	private Timestamp orderOutDate;
	/**
	 * @desc 出保说明
	 */
	@Size(min = 0,max = 200,message = "出保说明长度应小于200")
	private String orderOutDesc;
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

}
