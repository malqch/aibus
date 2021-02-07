package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * ${comments}
 * 
 * @date 2020-09-01 15:22:13
 */
@Data
@TableName("info_event_description")
public class InfoEventDescriptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_description_id", type = IdType.ID_WORKER)
	private Long eventDescriptionId;
	/**
	 * @desc 事件标签Id
	 */
	private Long eventTargetId;
	/**
	 * @desc 描述内容
	 */
	private String descriptionContent;
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
