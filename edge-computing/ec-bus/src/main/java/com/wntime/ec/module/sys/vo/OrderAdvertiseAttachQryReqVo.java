package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.OrderAdvertiseAttach;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@ApiModel(description = "广告附件表查询参数" )
@Data
public class OrderAdvertiseAttachQryReqVo extends OrderAdvertiseAttach {
     


	/**
	 * 排序
	 */
	@ApiModelProperty(hidden = true, name="orderBy",value="排序",required=false)
//	private String orderBy = " order by id desc ";
	private String orderBy = "";
}
