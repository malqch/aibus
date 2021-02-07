package com.wntime.fault.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 故障标签表
 * 
 * @date 2020-08-25 13:48:11
 */
@Data
@TableName("info_fault_target")
public class InfoFaultTargetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "fault_target_id", type = IdType.ID_WORKER)
	private Long faultTargetId;
	/**
	 * @desc 故障标签名称
	 */
	@NotNull(message = "故障标签名称不能为空")
	@Size(min = 0,max = 20,message = "故障标签名称不能超过20个字符")
	private String faultTargetName;
	/**
	 * @desc 标签分类编码
    @value link、value、char
	 */
	@NotNull(message = "故障标签分类编码不能为空")
	@Size(min = 0,max = 32,message = "故障标签名称不能超过32个字符")
	private String faultTargetGrope;
	/**
	 * @desc 故障标签编码
	 */
	@Size(max = 20,message = "故障标签编码不能超过20个字符")
	private String faultTargetCode;
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
