package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.OrderAdvertiseDelivery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@ApiModel(description = "广告投放单查询参数" )
@Data
public class OrderAdvertiseDeliveryQryReqVo extends OrderAdvertiseDelivery {
     
	/**
	* 起始时间_开始时间
	*/ 
	@ApiModelProperty(name="deliveryBeginStart", value="起始时间_开始时间", required=false) 
	private Date deliveryBeginStart;

	/**
	* 起始时间_结束时间
	*/ 
	@ApiModelProperty(name="deliveryBeginEnd", value="起始时间_结束时间", required=false) 
	private Date deliveryBeginEnd;

	/**
	* 截止时间_开始时间
	*/ 
	@ApiModelProperty(name="deliveryEndStart", value="截止时间_开始时间", required=false) 
	private Date deliveryEndStart;

	/**
	* 截止时间_结束时间
	*/ 
	@ApiModelProperty(name="deliveryEndEnd", value="截止时间_结束时间", required=false) 
	private Date deliveryEndEnd;



	/**
	 * 排序
	 */
	@ApiModelProperty(hidden = true, name="orderBy",value="排序",required=false)
//	private String orderBy = " order by id desc ";
	private String orderBy = "";
}
