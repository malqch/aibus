package com.wntime.fault.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 故障方案表
 * 
 * @date 2020-08-25 13:48:11
 */
@Data
@TableName("info_fault_suggestion")
public class InfoFaultSuggestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "fault_suggestion_id", type = IdType.ID_WORKER)
	private Long faultSuggestionId;
	/**
	 * @desc 故障标签Id
	 */
	private Long faultTargetId;
	/**
	 * @desc 建议内容
	 */
	private String suggestionContent;
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
	 * @desc 是否删除
	 */
	private Integer isDeleted;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;

}
