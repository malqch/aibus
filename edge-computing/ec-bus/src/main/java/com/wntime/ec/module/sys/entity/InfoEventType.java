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
* @创建时间：2020-08-27 02:37:26
* @文件描述：info_event_type @desc 事件类型表
*/
@ApiModel(description = "@desc 事件类型表" )
@Data
public class InfoEventType implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* @desc 主键
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="@desc 主键",name="eventTypeId",required=true) 
	protected Long eventTypeId;

	/**
	* @desc 事件类型
	*/ 
	@Size(max=20 ,message ="@desc 事件类型最多20个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 事件类型",name="eventTypeName",required=false) 
	protected String eventTypeName;

	/**
	* @desc 事件编码
	*/ 
	@Size(max=32 ,message ="@desc 事件编码最多32个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 事件编码",name="eventTypeCode",required=false) 
	protected String eventTypeCode;

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
