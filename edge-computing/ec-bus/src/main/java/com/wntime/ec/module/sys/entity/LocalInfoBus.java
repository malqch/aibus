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
* @创建时间：2020-08-27 02:39:19
* @文件描述：local_info_bus @desc 公交车表
*/
@ApiModel(description = "@desc 公交车表" )
@Data
public class LocalInfoBus implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* 本地ID
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="本地ID",name="localId",required=true) 
	protected Integer localId;

	/**
	* @desc 主键
	*/ 
	@ApiModelProperty(value="@desc 主键",name="busId",required=false) 
	protected Long busId;

	/**
	* @desc 公交车厂Id
	*/ 
	@ApiModelProperty(value="@desc 公交车厂Id",name="factoryId",required=false) 
	protected Long factoryId;

	/**
	* @desc 主键
	*/ 
	@ApiModelProperty(value="@desc 主键",name="busTypeId",required=false) 
	protected Long busTypeId;

	/**
	* @desc 车牌照号
	*/ 
	@Size(max=16 ,message ="@desc 车牌照号最多16个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 车牌照号",name="plateCode",required=false) 
	protected String plateCode;

	/**
	* @desc VIN编号
	*/ 
	@Size(max=64 ,message ="@desc VIN编号最多64个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc VIN编号",name="vinCode",required=false) 
	protected String vinCode;

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
