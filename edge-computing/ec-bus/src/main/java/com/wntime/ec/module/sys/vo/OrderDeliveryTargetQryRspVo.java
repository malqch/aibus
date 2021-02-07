package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.OrderDeliveryTarget;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "投放标签表查询结果" )
@Data
public class OrderDeliveryTargetQryRspVo extends OrderDeliveryTarget {

    protected String advertiseTargetCode;
    
}
