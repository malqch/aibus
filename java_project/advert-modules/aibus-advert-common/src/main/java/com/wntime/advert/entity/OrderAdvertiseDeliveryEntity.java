package com.wntime.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @desc 广告投放单
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Data
@TableName("order_advertise_delivery")
public class OrderAdvertiseDeliveryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long advertiseDeliveryId;
	/**
	 * @desc 投放方式
	 */
	private Long advertiseDeliveryType;
	/**
	 * @desc 起始时间
	 */
	@NotNull(message = "投放开始时间不能为空")
	private Timestamp deliveryBegin;
	/**
	 * @desc 截止时间
	 */
	@NotNull(message = "投放结束时间不能为空")
	private Timestamp deliveryEnd;
	/**
	 * @desc 审核状态
@values 0 草稿，1审核中，2，通过，3，投放中，4，未通过，9 下线
	 */
	@Range(min = 0,max = 9,message = "审核状态不合法")
	private Integer checkStatus;
	/**
	 * @desc 审核意见
	 */
	@Size(min = 0,max = 200,message = "审核意见不能超过200个字符")
	private String checkSuggest;
	/**
	 * @desc 是否插播
@values 0 不是，1 是
	 */
	@Range(min = 0,max = 1,message = "是否插播只能为0或1")
	private Integer isInterrupt;
	/**
	 * @desc 插播通知(需要初始化为"")
	 */
	@Size(min = 0,max = 200,message = "插播通知不能超过200个字符")
	private String interruptNotice = "";
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
	/**
	 * @desc 广告单号
	 */
	private String advertiseNo;

}
