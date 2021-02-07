package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.PlanBusService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@ApiModel(description = "营运计划表查询参数" )
@Data
public class PlanBusServiceQryReqVo extends PlanBusService {
     
	/**
	* 起始时间_开始时间
	*/ 
	@ApiModelProperty(name="beginDateStart", value="起始时间_开始时间", required=false) 
	private Date beginDateStart;

	/**
	* 起始时间_结束时间
	*/ 
	@ApiModelProperty(name="beginDateEnd", value="起始时间_结束时间", required=false) 
	private Date beginDateEnd;

	/**
	* 截止时间_开始时间
	*/ 
	@ApiModelProperty(name="endDateStart", value="截止时间_开始时间", required=false) 
	private Date endDateStart;

	/**
	* 截止时间_结束时间
	*/ 
	@ApiModelProperty(name="endDateEnd", value="截止时间_结束时间", required=false) 
	private Date endDateEnd;



	/**
	 * 排序
	 */
	@ApiModelProperty(hidden = true, name="orderBy",value="排序",required=false)
//	private String orderBy = " order by id desc ";
	private String orderBy = "";
}
