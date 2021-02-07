package com.wntime.fault.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/27 20:55
 */
@Setter
@Getter
@ApiModel
public class FaultInfoVo {
    @ApiModelProperty("最大值")
    private int max;
    @ApiModelProperty("故障级别代码")
    private String faultLevelCode;
    @ApiModelProperty("故障数量")
    private int number;
    @ApiModelProperty("故障级别名称")
    private String name;

    public FaultInfoVo() {
    }

    public FaultInfoVo(String faultLevelCode, String name) {
        this.faultLevelCode = faultLevelCode;
        this.name = name;
    }
}
