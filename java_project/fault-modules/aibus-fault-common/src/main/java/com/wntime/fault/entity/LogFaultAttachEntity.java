package com.wntime.fault.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 故障附件表
 * 
 * @date 2020-08-25 13:48:11
 */
@Data
@TableName("log_fault_attach")
public class LogFaultAttachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "fault_attach_id", type = IdType.ID_WORKER)
	private Long faultAttachId;
	/**
	 * @desc 故障日志Id
	 */
	private Long faultDetailId;
	/**
	 * @desc 故障标签Id
	 */
	private Long faultTargetId;

	@TableField(exist = false)
	private String faultTargetName;
	@TableField(exist = false)
	private String faultTargetGrope;
	@TableField(exist = false)
	private String faultTargetCode;
	/**
	 * @desc 数值
	 */
	private Double collectAttachValue;
	/**
	 * @desc 外联
	 */
	private Long collectAttachLink;
	@TableField(exist=false)
	private String collectAttachLinkName;
	/**
	 * @desc 字符
	 */
	private String collectAttachChar;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
