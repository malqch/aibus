package com.wntime.service.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author 79448
 * @date 2020/8/31 16:07
 */
@Setter
@Getter
public class QueryBusInfoFrom {
    @ApiModelProperty(value = "vin编码",required = true)
    @NotNull(message = "vin编码不能为空")
    private String vin;
}
