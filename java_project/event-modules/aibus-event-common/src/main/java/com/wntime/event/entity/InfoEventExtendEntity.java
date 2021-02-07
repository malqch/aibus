package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 事件拓展表
 * 
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("info_event_extend")
public class InfoEventExtendEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_extend_id", type = IdType.ID_WORKER)
	private Long eventExtendId;
	/**
	 * @desc 事件采集Id
	 */
	@NotNull(message = "事件采集不能为空")
	private Long collectEventId;
	/**
	 * @desc 事件标签Id
	 */
	@NotNull(message = "事件标签不能为空")
	private Long eventTargetId;
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

}
