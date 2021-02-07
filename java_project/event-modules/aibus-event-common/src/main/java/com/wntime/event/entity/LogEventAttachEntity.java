package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 事件附件表
 * 
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("log_event_attach")
public class LogEventAttachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_attach_id", type = IdType.ID_WORKER)
	private Long eventAttachId;
	/**
	 * @desc 采集日志Id
	 */
	private Long eventDetailId;
	/**
	 * @desc 事件标签Id
	 */
	private Long eventTargetId;
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

	public void init(){
		this.createdDate= DateUtils.getTimestamp();
	}

}
