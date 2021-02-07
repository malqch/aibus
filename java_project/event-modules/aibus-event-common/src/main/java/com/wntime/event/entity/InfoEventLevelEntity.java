package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 事件级别表
 * 
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("info_event_level")
public class InfoEventLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_level_id", type = IdType.ID_WORKER)
	private Long eventLevelId;
	/**
	 * @desc 事件级别
	 */
	@NotBlank(message = "事件级别名称不能为空")
	@Size(min = 0,max = 20,message = "事件级别名称长度应小于20")
	private String eventLevelName;
	/**
	 * @desc 事件级别
	 */
	@Size(min = 0,max = 20,message = "事件级别编码长度应小于20")
	private String eventLevelCode;
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
