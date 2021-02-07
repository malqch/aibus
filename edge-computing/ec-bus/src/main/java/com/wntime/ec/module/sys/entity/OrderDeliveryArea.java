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
* @创建时间：2020-11-06 04:02:00
* @文件描述：order_delivery_area 投放范围表
*/
@ApiModel(description = "投放范围表" )
@Data
public class OrderDeliveryArea implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="deliveryAreaId",required=true) 
	protected Long deliveryAreaId;

	/**
	* 广告投放Id
	*/ 
	@ApiModelProperty(value="广告投放Id",name="advertiseDeliveryId",required=false) 
	protected Long advertiseDeliveryId;

	/**
	* 公交线路Id
	*/ 
	@ApiModelProperty(value="公交线路Id",name="companyLineId",required=false) 
	protected Long companyLineId;

	/**
	* 线路车站Id
	*/ 
	@ApiModelProperty(value="线路车站Id",name="lineStationId",required=false) 
	protected Long lineStationId;




}
