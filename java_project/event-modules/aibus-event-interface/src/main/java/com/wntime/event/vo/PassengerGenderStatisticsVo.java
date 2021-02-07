package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.IntegerToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 11:46
 */
@Setter
@Getter
@ApiModel
public class PassengerGenderStatisticsVo {
    @ApiModelProperty("性别数据最大值")
    @JsonSerialize(using = IntegerToStringSerialize.class)
    private int max;
    @ApiModelProperty("性别代码 0 男 1女")
    private int sexCode;
    @ApiModelProperty("人数")
    @JsonSerialize(using = IntegerToStringSerialize.class)
    private int number;
}
