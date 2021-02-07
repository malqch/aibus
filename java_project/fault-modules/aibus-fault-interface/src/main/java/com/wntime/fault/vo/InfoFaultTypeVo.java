package com.wntime.fault.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * @desc 故障类型表
 * 
 * @date 2020-08-25 13:48:11
 */
@Setter
@Getter
public class InfoFaultTypeVo{

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultTypeId;
	/**
	 * @desc 故障类型名称
	 */
	private String faultTypeName;
	/**
	 * @desc 故障类型编码
	 */
	private String faultTypeCode;


}
