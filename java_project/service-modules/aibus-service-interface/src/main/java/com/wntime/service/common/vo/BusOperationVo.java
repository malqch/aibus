package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/10/29 15:13
 */
@Setter
@Getter
@ApiModel
public class BusOperationVo {

    @ApiModelProperty(value = "公交车id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long busId;

    @ApiModelProperty(value = "vin码")
    private String vinCode;

    @ApiModelProperty(value = "车牌")
    private String plateCode;

    @ApiModelProperty(value = "车辆状态 0 正常  1 告警（事件里的告警）  2 故障 （故障里的告警）")
    private int busStatus;

    @ApiModelProperty(value = "公交车所在站点id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long stationId;

    @ApiModelProperty(value = "途经站点状态 0 在当前站 1 驶离当前站")
    private int stationStatus;
}
