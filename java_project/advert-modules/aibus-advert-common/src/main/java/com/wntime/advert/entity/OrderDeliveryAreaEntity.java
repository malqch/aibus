package com.wntime.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

/**
 * @desc 投放范围表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Data
@TableName("order_delivery_area")
public class OrderDeliveryAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long deliveryAreaId;
	/**
	 * @desc 广告投放Id
	 */
	private Long advertiseDeliveryId;
	/**
	 * @desc 公交线路Id
	 */
	private Long companyLineId;
	/**
	 * @desc 线路车站Id
	 */
	private Long lineStationId;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
