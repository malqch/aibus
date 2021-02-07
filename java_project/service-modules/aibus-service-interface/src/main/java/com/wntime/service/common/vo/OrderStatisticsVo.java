package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.IntegerToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 11:49
 */
@Setter
@Getter
@ApiModel
public class OrderStatisticsVo {

    @ApiModelProperty(value = "订单数量最大值")
    @JsonSerialize(using = IntegerToStringSerialize.class)
    private int max;

    @ApiModelProperty(value = "订单类型")
    private int orderType;

    @ApiModelProperty(value = "订单量")
    @JsonSerialize(using = IntegerToStringSerialize.class)
    private int number;

    @ApiModelProperty(value = "订单类型名称")
    private String name;
}
