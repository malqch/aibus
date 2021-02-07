package com.wntime.fault.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * @desc 故障级别表
 * 
 * @date 2020-08-25 13:48:11
 */
@Setter
@Getter
public class InfoFaultLevelVo{

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultLevelId;
	/**
	 * @desc 故障级别
	 */
	private String faultLevelName;
	/**
	 * @desc 故障级别
	 */
	private String faultLevelCode;

}
