package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/2 17:24
 */
@Setter
@Getter
public class LineStationVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long lineStationId;
    /**
     * @desc 公交线路Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long companyLineId;
    /**
     * @desc 公交车站Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long busStationId;
    /**
     * @desc 序号
     */
    private Integer stationOrder;

}
