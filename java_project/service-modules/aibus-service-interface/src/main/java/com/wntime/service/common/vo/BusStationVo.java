package com.wntime.service.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * @desc 公交车站表
 * 
 * @date 2020-08-25 14:00:25
 */
@Setter
@Getter
@ApiModel
public class BusStationVo{
	@ApiModelProperty(value = "站点id")
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long busStationId;
	@ApiModelProperty(value = "站点名称")
	private String busStationName;
	@ApiModelProperty(value = "站点代码")
	private String busStationCode;
}
