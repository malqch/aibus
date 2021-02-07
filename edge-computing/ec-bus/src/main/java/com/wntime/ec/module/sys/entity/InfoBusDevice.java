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
* @创建时间：2020-08-27 02:43:28
* @文件描述：info_bus_device @desc AI设备表
*/
@ApiModel(description = "@desc AI设备表" )
@Data
public class InfoBusDevice implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* @desc 设备编号
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@Size(max=64 ,message ="@desc 设备编号最多64个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 设备编号",name="deviceCode",required=true) 
	protected String deviceCode;

	/**
	* @desc 设备描述编码
	*/ 
	@Size(max=64 ,message ="@desc 设备描述编码最多64个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 设备描述编码",name="deviceDescCode",required=false) 
	protected String deviceDescCode;

	/**
	* @desc 主键
	*/ 
	@ApiModelProperty(value="@desc 主键",name="busDeviceId",required=false) 
	protected Long busDeviceId;

	/**
	* @desc 设备类型Id
	*/ 
	@ApiModelProperty(value="@desc 设备类型Id",name="deviceTypeId",required=false) 
	protected Long deviceTypeId;

	/**
	* @desc 公交车Id
	*/ 
	@ApiModelProperty(value="@desc 公交车Id",name="busId",required=false) 
	protected Long busId;

	/**
	* @desc 设备名称
	*/ 
	@Size(max=32 ,message ="@desc 设备名称最多32个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="@desc 设备名称",name="deviceName",required=false) 
	protected String deviceName;

	/**
	* @desc 设备状态
    @value 连接中，在线，离线
	*/ 
	@ApiModelProperty(value="@desc 设备状态 @value 连接中，在线，离线",name="deviceStatus",required=false)
	protected Integer deviceStatus;

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
