package com.wntime.fault.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 故障扩展表
 * 
 * @date 2020-08-25 13:48:11
 */
@Data
@TableName("info_fault_extend")
public class InfoFaultExtendEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "fault_extend_id", type = IdType.ID_WORKER)
	private Long faultExtendId;
	/**
	 * @desc 故障采集Id
	 */
	private Long collectFaultId;
	/**
	 * @desc 故障标签Id
	 */
	@NotNull(message = "故障标签ID不能为空")
	private Long faultTargetId;

	@TableField(exist = false)
	private String faultTargetName;

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

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;

}
