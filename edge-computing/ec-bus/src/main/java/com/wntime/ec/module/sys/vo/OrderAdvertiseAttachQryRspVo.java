package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.OrderAdvertiseAttach;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "广告附件表查询结果" )
@Data
public class OrderAdvertiseAttachQryRspVo extends OrderAdvertiseAttach {

    protected String positionCode;
}
