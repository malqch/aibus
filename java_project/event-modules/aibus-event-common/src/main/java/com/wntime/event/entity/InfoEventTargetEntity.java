package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 事件标签表
 * 
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("info_event_target")
public class InfoEventTargetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_target_id", type = IdType.ID_WORKER)
	private Long eventTargetId;
	/**
	 * @desc 事件标签
	 */
	@NotBlank(message = "事件标签名称不能为空")
	@Size(min = 0,max = 20,message = "事件标签名称长度应小于20")
	private String eventTargetName;
	/**
	 * @desc 标签分类编码
    @value link、value、char
	 */
	@NotBlank(message = "事件标签分类不能为空")
	private String eventTargetGrope;
	/**
	 * @desc 事件标签编码
	 */
	@Size(min = 0,max = 20,message = "事件标签编码长度应小于20")
	private String eventTargetCode;
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
