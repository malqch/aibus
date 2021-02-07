package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 79448
 * @date 2020/10/30 14:32
 */
@Setter
@Getter
@ApiModel
public class BusLineStationVo {

    @ApiModelProperty(value = "公交公司id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long companyId;
    @ApiModelProperty(value = "公交线路id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long companyLineId;
    @ApiModelProperty(value = "公交线路代码")
    private String companyLineCode;
    @ApiModelProperty(value = "公交线路名称")
    private String companyLineName;
    @ApiModelProperty(value = "公交线路站点列表")
    private List<BusStationVo> stations;
}
