package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;


/**
 * @desc 事件类型表
 * 
 * @date 2020-08-25 13:34:24
 */
@Setter
@Getter
public class InfoEventTypeVo {


	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventTypeId;
	/**
	 * @desc 事件类型
	 */
	private String eventTypeName;
	/**
	 * @desc 事件编码
	 */
	private String eventTypeCode;


}
