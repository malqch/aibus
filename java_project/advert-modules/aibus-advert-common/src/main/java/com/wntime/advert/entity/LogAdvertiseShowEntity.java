package com.wntime.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

/**
 * @desc 广告播放日志
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Data
@TableName("log_advertise_show")
public class LogAdvertiseShowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long advertiseShowId;
	/**
	 * @desc 广告投放Id
	 */
	private Long advertiseDeliveryId;

	@TableField(exist = false)
	private String advertiseNo;

	/**
	 * @desc 广告位Id
	 */
	private Long advertisePositionId;

	@TableField(exist = false)
	private String positionDesc;

	@TableField(exist = false)
	private String positionGroup;
	/**
	 * @desc 播放时长
	 */
	private Double showTimes;
	/**
	 * @desc 素材类型
	 */
	private Long attachType;

	private Long busId;

	@TableField(exist = false)
	private String vinCode;

	@TableField(exist = false)
	private String plateCode;

	private Long companyLineId;

	@TableField(exist = false)
	private String companyLineName;

	private int peopleCount;

	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
