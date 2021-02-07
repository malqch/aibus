package com.wntime.service.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/5 11:34
 */
@Setter
@Getter
@ApiModel
public class CompanyLineNameVo {
    @ApiModelProperty(value = "公交线路名称")
    private String name;
    @ApiModelProperty(value = "公交线路值")
    private String value;
}
