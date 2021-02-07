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

/**
* @创建时间：2020-11-07 04:00:36
* @文件描述：order_advertise_delivery 广告投放单
*/
@ApiModel(description = "广告投放单" )
@Data
public class OrderAdvertiseDelivery implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="advertiseDeliveryId",required=true) 
	protected Long advertiseDeliveryId;

	/**
	* 投放方式
	*/ 
	@Size(max=50 ,message ="投放方式最多50个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="投放方式",name="advertiseDeliveryType",required=false) 
	protected String advertiseDeliveryType;

	/**
	* 起始时间
	*/ 
	@ApiModelProperty(value="起始时间",name="deliveryBegin",required=false) 
	protected Date deliveryBegin;

	/**
	* 截止时间
	*/ 
	@ApiModelProperty(value="截止时间",name="deliveryEnd",required=false) 
	protected Date deliveryEnd;

	/**
	* 审核状态 0 草稿，1审核中，2，通过，3，投放中，4，未通过，9 下线
	*/ 
	@ApiModelProperty(value="审核状态 0 草稿，1审核中，2，通过，3，投放中，4，未通过，9 下线",name="checkStatus",required=false) 
	protected Integer checkStatus;

	/**
	* 审核意见
	*/ 
	@Size(max=200 ,message ="审核意见最多200个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="审核意见",name="checkSuggest",required=false) 
	protected String checkSuggest;

	/**
	* 是否插播  0 不是，1 是
	*/ 
	@ApiModelProperty(value="是否插播  0 不是，1 是",name="isInterrupt",required=false) 
	protected Integer isInterrupt;

	/**
	* 插播通知
	*/ 
	@Size(max=500 ,message ="插播通知最多500个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="插播通知",name="interruptNotice",required=false) 
	protected String interruptNotice;

	/**
	* 广告单号
	*/ 
	@Size(max=100 ,message ="广告单号最多100个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告单号",name="advertiseNo",required=false) 
	protected String advertiseNo;

	protected Long companyLineId;


}
