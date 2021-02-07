package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.InfoFaultType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@ApiModel(description = "@desc 故障类型表查询参数" )
@Data
public class InfoFaultTypeQryReqVo extends InfoFaultType {
     
	/**
	* @desc 创建时间_开始时间
	*/ 
	@ApiModelProperty(name="createdDateStart", value="@desc 创建时间_开始时间", required=false) 
	private Date createdDateStart;

	/**
	* @desc 创建时间_结束时间
	*/ 
	@ApiModelProperty(name="createdDateEnd", value="@desc 创建时间_结束时间", required=false) 
	private Date createdDateEnd;

	/**
	* @desc 修改时间_开始时间
	*/ 
	@ApiModelProperty(name="modifiedDateStart", value="@desc 修改时间_开始时间", required=false) 
	private Date modifiedDateStart;

	/**
	* @desc 修改时间_结束时间
	*/ 
	@ApiModelProperty(name="modifiedDateEnd", value="@desc 修改时间_结束时间", required=false) 
	private Date modifiedDateEnd;



	/**
	 * 排序
	 */
	@ApiModelProperty(hidden = true, name="orderBy",value="排序",required=false)
//	private String orderBy = " order by id desc ";
	private String orderBy = "";
}
