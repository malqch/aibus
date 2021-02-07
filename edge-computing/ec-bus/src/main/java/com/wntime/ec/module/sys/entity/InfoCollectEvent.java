package com.wntime.ec.module.sys.entity ;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
* @创建时间：2020-08-27 02:35:28
* @文件描述：info_collect_event @desc 事件采集表
*/
@ApiModel(description = "@desc 事件采集表" )
@Data
public class InfoCollectEvent implements Serializable {
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
	* @desc 事件类型Id
	*/ 
	@ApiModelProperty(value="@desc 事件类型Id",name="eventTypeId",required=false) 
	protected Long eventTypeId;

	/**
	* @desc 事件标签Id
	*/ 
	@ApiModelProperty(value="@desc 事件标签Id",name="eventTargetId",required=false) 
	protected Long eventTargetId;

	/**
	* @desc 事件级别Id
	*/ 
	@ApiModelProperty(value="@desc 事件级别Id",name="eventLevelId",required=false) 
	protected Long eventLevelId;

	/**
	* @desc 设备类型Id
	*/ 
	@ApiModelProperty(value="@desc 设备类型Id",name="deviceTypeId",required=false) 
	protected Long deviceTypeId;

	/**
	* @desc 事件大类
	*/ 
	@ApiModelProperty(value="@desc 事件大类",name="eventType",required=false) 
	protected Integer eventType;

	/**
	* @desc 事件小类
	*/ 
	@ApiModelProperty(value="@desc 事件小类",name="eventDetail",required=false) 
	protected Integer eventDetail;

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
