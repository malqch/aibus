package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;


/**
 * @desc 事件级别表
 * 
 * @date 2020-08-25 13:34:24
 */
@Setter
@Getter
public class InfoEventLevelVo  {

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventLevelId;
	/**
	 * @desc 事件级别
	 */
	private String eventLevelName;
	/**
	 * @desc 事件级别
	 */
	private String eventLevelCode;

}
