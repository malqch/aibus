package com.wntime.ec.module.sys.entity ;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
* @创建时间：2020-11-10 10:01:33
* @文件描述：order_advertise_attach 广告附件表
*/
@ApiModel(description = "广告附件表" )
@Data
public class OrderAdvertiseAttach implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="advertiseAttachId",required=true)
	@JSONField(serializeUsing = ToStringSerializer.class)
	protected Long advertiseAttachId;

	/**
	* 广告投放Id
	*/ 
	@ApiModelProperty(value="广告投放Id",name="advertiseDeliveryId",required=false)
	@JSONField(serializeUsing = ToStringSerializer.class)
	protected Long advertiseDeliveryId;

	/**
	* 广告位Id
	*/ 
	@ApiModelProperty(value="广告位Id",name="advertisePositionId",required=false)
	@JSONField(serializeUsing = ToStringSerializer.class)
	protected Long advertisePositionId;

	/**
	* 播放时长
	*/ 
	@ApiModelProperty(value="播放时长",name="showTimes",required=false) 
	protected BigDecimal showTimes;

	/**
	* 素材类型
	*/ 
	@ApiModelProperty(value="素材类型",name="attachType",required=false) 
	protected Integer attachType;

	/**
	* 素材地址
	*/ 
	@Size(max=500 ,message ="素材地址最多500个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="素材地址",name="attachLink",required=false) 
	protected String attachLink;

	/**
	* 附件后缀
	*/ 
	@Size(max=20 ,message ="附件后缀最多20个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="附件后缀",name="suffix",required=false) 
	protected String suffix;

	@JSONField(serializeUsing = ToStringSerializer.class)
	protected Long companyLineId;


}
