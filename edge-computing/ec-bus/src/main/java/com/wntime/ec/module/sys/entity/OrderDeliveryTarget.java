package com.wntime.ec.module.sys.entity;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
* @创建时间：2020-11-06 04:02:52
* @文件描述：order_delivery_target 投放标签表
*/
@ApiModel(description = "投放标签表" )
@Data
public class OrderDeliveryTarget implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="deliveryTargetId",required=true) 
	protected Long deliveryTargetId;

	/**
	* 广告投放Id
	*/ 
	@ApiModelProperty(value="广告投放Id",name="advertiseDeliveryId",required=false) 
	protected Long advertiseDeliveryId;

	/**
	* 广告标签Id
	*/ 
	@ApiModelProperty(value="广告标签Id",name="advertiseTargetId",required=false) 
	protected Long advertiseTargetId;

	protected Long companyLineId;

}
