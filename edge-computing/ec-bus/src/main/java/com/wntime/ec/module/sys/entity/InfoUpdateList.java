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
* @创建时间：2020-08-27 02:39:00
* @文件描述：info_update_list @desc 更新信息表
*/
@ApiModel(description = "@desc 更新信息表" )
@Data
public class InfoUpdateList implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* @desc 主键
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="@desc 主键",name="updateListId",required=true) 
	protected Long updateListId;

	/**
	* @desc 更新类型
	*/ 
	@Size(max=32 ,message ="@desc 更新类型最多32个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 更新类型",name="updateType",required=false) 
	protected String updateType;

	/**
	* @desc 更新描述
	*/ 
	@Size(max=128 ,message ="@desc 更新描述最多128个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 更新描述",name="updateDesc",required=false) 
	protected String updateDesc;

	/**
	* @desc 文件名称
	*/ 
	@Size(max=128 ,message ="@desc 文件名称最多128个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 文件名称",name="originalFileName",required=false) 
	protected String originalFileName;

	/**
	* @desc 文件路径
	*/ 
	@Size(max=65535 ,message ="@desc 文件路径最多65535个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 文件路径",name="updateUrl",required=false) 
	protected String updateUrl;

	/**
	* @desc 是否发布
	*/ 
	@ApiModelProperty(value="@desc 是否发布",name="isPublished",required=false) 
	protected Integer isPublished;

	/**
	* @desc 发布时间
	*/ 
	@ApiModelProperty(value="@desc 发布时间",name="publishDate",required=false) 
	protected Date publishDate;

	/**
	* @desc 是否删除
	*/ 
	@ApiModelProperty(value="@desc 是否删除",name="isDeleted",required=false) 
	protected Integer isDeleted;

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
