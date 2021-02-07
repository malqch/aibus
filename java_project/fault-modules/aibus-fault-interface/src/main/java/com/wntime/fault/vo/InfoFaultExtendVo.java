package com.wntime.fault.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * @desc 故障扩展表
 * 
 * @date 2020-08-25 13:48:11
 */
@Setter
@Getter
public class InfoFaultExtendVo{

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultExtendId;
	/**
	 * @desc 故障采集Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long collectEventId;
	/**
	 * @desc 故障标签Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultTargetId;


}
