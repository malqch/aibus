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
 * @desc 故障采集表
 * 
 * @date 2020-08-25 13:48:11
 */
@Data
@TableName("info_collect_fault")
public class InfoCollectFaultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "collect_fault_id", type = IdType.ID_WORKER)
	private Long collectFaultId;
	/**
	 * @desc 采集内容
	 */
	@NotNull(message = "故障采集内容不能为空")
	@Size(min = 0,max = 20,message = "故障采集内容不能超过20个字符")
	private String collectFault;
	/**
	 * @desc 采集编码
	 */
	@NotNull(message = "故障采集编码不能为空")
	@Size(min = 0,max = 20,message = "故障采集编码不能超过20个字符")
	private String collectCode;
	/**
	 * @desc 故障类型Id
	 */
	@NotNull(message = "故障类型id不能为空")
	private Long faultTypeId;

	@TableField(exist = false)
	private String faultTypeName;
	/**
	 * @desc 故障标签Id
	 */
	@NotNull(message = "故障标签id不能为空")
	private Long faultTargetId;

	@TableField(exist = false)
	private String faultTargetName;

	/**
	 * @desc 标签方案ID
	 */
	@TableField(exist = false)
	private Long faultSuggestionId;
	/**
	 * @desc 建议内容
	 */
	@TableField(exist = false)
	@Size(max = 200,message = "故障方案不能超过200个字符")
	private String suggestionContent;

	/**
	 * @desc 故障级别Id
	 */
	@NotNull(message = "故障等级id不能为空")
	private Long faultLevelId;

	@TableField(exist = false)
	private String faultLevelName;
	/**
	 * @desc 故障ID
	 */
	@NotNull(message = "故障ID不能为空")
	@Size(min = 0,max = 64,message = "故障ID不能超过64个字符")
	private String faultType;
	/**
	 * @desc 故障索引码
	 */
	@NotNull(message = "故障索引码不能为空")
	@Size(min = 0,max = 64,message = "故障索引码不能超过64个字符")
	private String faultDetail;
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
