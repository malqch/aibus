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
import java.math.BigDecimal;

//import org.hibernate.validator.constraints.NotEmpty;

/**
* @创建时间：2020-11-03 02:59:57
* @文件描述：info_bus_station 公交车站表
*/
@ApiModel(description = "公交车站表" )
@Data
public class InfoBusStation implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* 
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="",name="busStationId",required=true) 
	protected Long busStationId;

	/**
	* 公交车站名称
	*/ 
	@Size(max=50 ,message ="公交车站名称最多50个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="公交车站名称",name="busStationName",required=false) 
	protected String busStationName;

	/**
	* 公交车站编码
	*/ 
	@Size(max=50 ,message ="公交车站编码最多50个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="公交车站编码",name="busStationCode",required=false) 
	protected String busStationCode;

	/**
	* 经度
	*/ 
	@ApiModelProperty(value="经度",name="busStationLongitude",required=false) 
	protected BigDecimal busStationLongitude;

	/**
	* 纬度
	*/ 
	@ApiModelProperty(value="纬度",name="busStationLatitude",required=false) 
	protected BigDecimal busStationLatitude;

	/**
	* 偏差(米)
	*/ 
	@ApiModelProperty(value="偏差(米)",name="busStationDeviation",required=false) 
	protected Integer busStationDeviation;




}
