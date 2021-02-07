package com.wntime.service.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/10 17:29
 */
@Setter
@Getter
@ApiModel
public class FactorySaleVo {
    @ApiModelProperty(value = "公交公司名称")
    private String name;
    @ApiModelProperty(value = "公交公司坐标")
    private Double[] value;
    @ApiModelProperty(value="公家公司订单数")
    private int number;
}
