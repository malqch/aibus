package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.IntegerToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/27 15:46
 */
@Setter
@Getter
@ApiModel
public class HarmfulAlarmVo {
    @ApiModelProperty("检测次数最大值")
    @JsonSerialize(using = IntegerToStringSerialize.class)
    private int max;
    @ApiModelProperty("检测类型")
    private int alarmType;
    @JsonSerialize(using = IntegerToStringSerialize.class)
    @ApiModelProperty("检测次数")
    private int number;
    @ApiModelProperty("检测名称")
    private String name;
}

