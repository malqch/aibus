package com.wntime.modules.monitor.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/28 19:29
 */
@Setter
@Getter
@ApiModel
public class BusLineInfoVo {
    @ApiModelProperty("公交车id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long busId;
    @ApiModelProperty("公交公司名称")
    private String companyName;
    @ApiModelProperty("公交线路名称")
    private String companyLineName;
    @ApiModelProperty("车牌号")
    private String plateCode;
    @ApiModelProperty("VIN码")
    private String vinCode;
    @ApiModelProperty("运营时间段")
    private String serviceTime;


}
