package com.wntime.ec.module.sys.vo;

import com.wntime.ec.module.sys.entity.InfoLineStation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(description = "线路车站表查询结果" )
@Data
public class InfoLineStationQryRspVo extends InfoLineStation {
    /**
     * 经度
     */
    @ApiModelProperty(value="经度",name="busStationLongitude",required=false)
    protected BigDecimal busStationLongitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度",name="busStationLatitude",required=false)
    protected BigDecimal busStationLatitude;

    /**
     * 偏差(米)
     */
    @ApiModelProperty(value="偏差(米)",name="busStationDeviation",required=false)
    protected Integer busStationDeviation;

}
