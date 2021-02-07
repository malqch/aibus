package com.wntime.fault.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * @desc 故障标签表
 * 
 * @date 2020-08-25 13:48:11
 */
@Setter
@Getter
public class InfoFaultTargetVo{

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long faultTargetId;
	/**
	 * @desc 故障标签名称
	 */
	private String faultTargetName;
	/**
	 * @desc 标签分类编码
    @value link、value、char
	 */
	private String faultTargetGrope;
	/**
	 * @desc 故障标签编码
	 */
	private String faultTargetCode;


}
