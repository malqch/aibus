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
 * @desc 事件类型表
 * 
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("info_event_type")
public class InfoEventTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_type_id", type = IdType.ID_WORKER)
	private Long eventTypeId;
	/**
	 * @desc 事件类型
	 */
	@NotBlank(message = "事件类型名称不能为空")
	@Size(min = 0,max = 20,message = "事件类型名称长度应小于20")
	private String eventTypeName;
	/**
	 * @desc 事件编码
	 */
	@Size(min = 0,max = 20,message = "事件类型编码长度应小于20")
	private String eventTypeCode;
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
