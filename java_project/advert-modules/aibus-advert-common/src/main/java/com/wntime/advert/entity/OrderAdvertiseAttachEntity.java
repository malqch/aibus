package com.wntime.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

/**
 * @desc 广告附件表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Data
@TableName("order_advertise_attach")
public class OrderAdvertiseAttachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long advertiseAttachId;
	/**
	 * @desc 广告投放Id
	 */
	private Long advertiseDeliveryId;
	/**
	 * @desc 广告位Id
	 */
	private Long advertisePositionId;
	/**
	 * @desc 播放时长
	 */
	private Double showTimes;
	/**
	 * @desc 素材类型
	 */
	private Integer attachType;
	/**
	 * @desc 素材地址
	 */
	private String attachLink;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
