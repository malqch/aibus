package com.wntime.ec.module.sys.entity ;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

//import org.hibernate.validator.constraints.NotEmpty;

/**
* @创建时间：2020-08-27 02:35:45
* @文件描述：info_collect_fault @desc 故障采集表
*/
@ApiModel(description = "@desc 故障采集表" )
@Data
public class InfoCollectFault implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* @desc 主键
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="@desc 主键",name="collectEventId",required=true) 
	protected Long collectEventId;

	/**
	* @desc 采集内容
	*/ 
	@Size(max=20 ,message ="@desc 采集内容最多20个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 采集内容",name="collectEvent",required=false) 
	protected String collectEvent;

	/**
	* @desc 采集编码
	*/ 
	@Size(max=20 ,message ="@desc 采集编码最多20个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 采集编码",name="collectCode",required=false) 
	protected String collectCode;

	/**
	* @desc 故障类型Id
	*/ 
	@ApiModelProperty(value="@desc 故障类型Id",name="faultTypeId",required=false) 
	protected Long faultTypeId;

	/**
	* @desc 故障标签Id
	*/ 
	@ApiModelProperty(value="@desc 故障标签Id",name="faultTargetId",required=false) 
	protected Long faultTargetId;

	/**
	* @desc 故障级别Id
	*/ 
	@ApiModelProperty(value="@desc 故障级别Id",name="faultLevelId",required=false) 
	protected Long faultLevelId;

	/**
	* @desc 故障ID
	*/ 
	@Size(max=64 ,message ="@desc 故障ID最多64个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 故障ID",name="faultType",required=false) 
	protected String faultType;

	/**
	* @desc 故障索引码
	*/ 
	@Size(max=64 ,message ="@desc 故障索引码最多64个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 故障索引码",name="faultDetail",required=false) 
	protected String faultDetail;

	/**
	* @desc 是否删除
	*/ 
	@ApiModelProperty(value="@desc 是否删除",name="isDeleted",required=false) 
	protected Integer isDeleted;

	/**
	* @desc 是否启用
	*/ 
	@ApiModelProperty(value="@desc 是否启用",name="isEnabled",required=false) 
	protected Integer isEnabled;

	/**
	* @desc 创建人
	*/ 
	@ApiModelProperty(value="@desc 创建人",name="createdBy",required=false) 
	protected Long createdBy;

	/**
	* @desc 创建时间
	*/ 
	@ApiModelProperty(value="@desc 创建时间",name="createdDate",required=false) 
	protected Date createdDate;

	/**
	* @desc 修改人
	*/ 
	@ApiModelProperty(value="@desc 修改人",name="modifiedBy",required=false) 
	protected Long modifiedBy;

	/**
	* @desc 修改时间
	*/ 
	@ApiModelProperty(value="@desc 修改时间",name="modifiedDate",required=false) 
	protected Date modifiedDate;




}
