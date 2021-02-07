package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/27 17:36
 */
@Setter
@Getter
@ApiModel
public class BusCompanyBaseInfoVo {

    @ApiModelProperty(value = "公交公司id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long companyId;

    /**
     * @desc 公交公司名称
     */
    @ApiModelProperty(value = "公交公司名称")
    private String companyName;

}
