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
* @创建时间：2020-08-27 02:37:06
* @文件描述：info_event_target @desc 事件标签表
*/
@ApiModel(description = "@desc 事件标签表" )
@Data
public class InfoEventTarget implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* @desc 主键
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="@desc 主键",name="eventTargetId",required=true) 
	protected Long eventTargetId;

	/**
	* @desc 事件标签
	*/ 
	@Size(max=20 ,message ="@desc 事件标签最多20个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 事件标签",name="eventTargetName",required=false) 
	protected String eventTargetName;

	/**
	* @desc 标签分类编码
    @value link、value、char
	*/ 
	@Size(max=32 ,message ="@desc 标签分类编码 @value link、value、char最多32个字符",groups=AddGroup.class)
	@ApiModelProperty(value="@desc 标签分类编码 @value link、value、char",name="eventTargetGrope",required=false)
	protected String eventTargetGrope;

	/**
	* @desc 事件标签编码
	*/ 
	@Size(max=32 ,message ="@desc 事件标签编码最多32个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 事件标签编码",name="eventTargetCode",required=false) 
	protected String eventTargetCode;

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
