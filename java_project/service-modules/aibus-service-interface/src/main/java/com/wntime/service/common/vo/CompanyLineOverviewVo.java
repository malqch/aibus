package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/5 11:00
 */
@Setter
@Getter
@ApiModel
public class CompanyLineOverviewVo {

    @ApiModelProperty(value = "公交公司id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long companyId;

    @ApiModelProperty(value = "公交线路id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long companyLineId;

    @ApiModelProperty(value = "公交线路名称")
    private String companyLineName;

    @ApiModelProperty(value = "公交线路代码")
    private String companyLineCode;

    @ApiModelProperty(value = "起点站id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long fromBusStationId;

    @ApiModelProperty(value = "起点站名称")
    private String fromBusStationName;

    @ApiModelProperty(value = "起点站纬度")
    private double fromBusStationLatitude;

    @ApiModelProperty(value = "起点站经度")
    private double fromBusStationLongitude;

    @ApiModelProperty(value = "终点站id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long toBusStationId;

    @ApiModelProperty(value = "终点站名称")
    private String toBusStationName;

    @ApiModelProperty(value = "终点站维度")
    private double toBusStationLatitude;

    @ApiModelProperty(value = "终点站经度")
    private double toBusStationLongitude;
}
