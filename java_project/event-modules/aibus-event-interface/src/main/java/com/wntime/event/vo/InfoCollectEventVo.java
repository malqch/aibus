package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;


/**
 * @desc 事件采集表
 * 
 * @date 2020-08-25 13:34:24
 */
@Setter
@Getter
public class InfoCollectEventVo  {

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
	 * @desc 事件类型Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventTypeId;
	/**
	 * @desc 事件标签Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventTargetId;
	/**
	 * @desc 事件级别Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventLevelId;
	/**
	 * @desc 设备类型Id
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long deviceTypeId;
	/**
	 * @desc 事件大类
	 */
	private Integer eventType;
	/**
	 * @desc 事件小类
	 */
	private Integer eventDetail;
}
