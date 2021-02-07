package com.wntime.service.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 16:08
 */
@Setter
@Getter
@ApiModel
public class BusInfoVo {

    @ApiModelProperty(value = "公交车id")
    private Long busId;

    @ApiModelProperty(value = "公交车所在的区域")
    private String areaName;
    @ApiModelProperty(value = "公交公司名称")
    private String companyName;
    @ApiModelProperty(value = "公交线路")
    private String companyLineName;
    @ApiModelProperty(value = "车牌")
    private String plateCode;
    @ApiModelProperty(value = "vin码")
    private String vinCode;

    /**
     * 荷载人数
     */
    @ApiModelProperty(value = "荷载人数")
    private int peopleLoadCount;
}
