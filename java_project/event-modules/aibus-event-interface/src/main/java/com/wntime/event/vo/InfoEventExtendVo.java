package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;


/**
 * @desc 事件拓展表
 * 
 * @date 2020-08-25 13:34:24
 */
@Setter
@Getter
public class InfoEventExtendVo  {

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventExtendId;
	/**
	 * @desc 事件采集Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long collectEventId;
	/**
	 * @desc 事件标签Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventTargetId;

}
