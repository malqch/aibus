package com.wntime.ec.module.sys.entity ;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

//import org.hibernate.validator.constraints.NotEmpty;

/**
* @创建时间：2020-11-03 03:01:13
* @文件描述：info_line_station 线路车站表
*/
@ApiModel(description = "线路车站表" )
@Data
public class InfoLineStation implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="lineStationId",required=true) 
	protected Long lineStationId;

	/**
	* 公交线路Id
	*/ 
	@ApiModelProperty(value="公交线路Id",name="companyLineId",required=false) 
	protected Long companyLineId;

	/**
	* 公交车站Id
	*/ 
	@ApiModelProperty(value="公交车站Id",name="busStationId",required=false) 
	protected Long busStationId;

	/**
	* 序号
	*/ 
	@ApiModelProperty(value="序号",name="stationOrder",required=false) 
	protected Integer stationOrder;




}
