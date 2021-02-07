package com.wntime.ec.module.sys.entity ;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

//import org.hibernate.validator.constraints.NotEmpty;

/**
* @创建时间：2020-11-03 03:01:47
* @文件描述：plan_bus_service 营运计划表
*/
@ApiModel(description = "营运计划表" )
@Data
public class PlanBusService implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="planServiceId",required=true) 
	protected Long planServiceId;

	/**
	* 公交车Id
	*/ 
	@ApiModelProperty(value="公交车Id",name="busId",required=false) 
	protected Long busId;

	/**
	* 公交线路Id
	*/ 
	@ApiModelProperty(value="公交线路Id",name="companyLineId",required=false) 
	protected Long companyLineId;

	/**
	* 起始时间
	*/ 
	@ApiModelProperty(value="起始时间",name="beginDate",required=false) 
	protected Date beginDate;

	/**
	* 截止时间
	*/ 
	@ApiModelProperty(value="截止时间",name="endDate",required=false) 
	protected Date endDate;




}
