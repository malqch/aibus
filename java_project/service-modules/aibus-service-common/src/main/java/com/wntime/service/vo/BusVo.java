package com.wntime.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/31 13:52
 */
@Setter
@Getter
@ApiModel
public class BusVo {
    @ApiModelProperty("公交车id")
    private Long busId;
    /**
     * @desc 公交车厂Id
     */
    @ApiModelProperty("公交车厂Id")
    private Long factoryId;
    /**
     * @desc 主键
     */
    @ApiModelProperty("公交车类型")
    private Long busTypeId;
    /**
     * @desc 车牌照号
     */
    @ApiModelProperty("车牌照号")
    private String plateCode;
    /**
     * @desc VIN编号
     */
    @ApiModelProperty("VIN编号")
    private String vinCode;
    /**
     * @desc 车辆状态
     */
    @ApiModelProperty("车辆状态")
    private Long busStatus;
}
