package com.wntime.fault.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * @desc 故障采集表
 * 
 * @date 2020-08-25 13:48:11
 */
@Setter
@Getter
public class InfoCollectFaultVo  {


	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long collectEventId;
	/**
	 * @desc 采集内容
	 */
	private String collectEvent;
	/**
	 * @desc 采集编码
	 */
	private String collectCode;
	/**
	 * @desc 故障类型Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultTypeId;
	/**
	 * @desc 故障标签Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultTargetId;
	/**
	 * @desc 故障级别Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultLevelId;
	/**
	 * @desc 故障ID
	 */
	private String faultType;
	/**
	 * @desc 故障索引码
	 */
	private String faultDetail;


}
