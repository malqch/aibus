package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.IntegerToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/27 15:14
 */
@Setter
@Getter
@ApiModel
public class PassengerFlowVo {
    @ApiModelProperty("人数最大值")
    private int max;
    @ApiModelProperty("分组 0|1|2  上车(成年)|下车（老年）|车内（儿童）")
    private int group;
    @ApiModelProperty("人数值")
    private int number;

    private String name;

    public PassengerFlowVo() {
    }

    public PassengerFlowVo(int group) {
        this.group = group;
    }
}
