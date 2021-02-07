package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;


/**
 * @desc 事件标签表
 * 
 * @date 2020-08-25 13:34:24
 */
@Setter
@Getter
public class InfoEventTargetVo {

	/**
	 * @desc 主键
	 */
	@JsonSerialize(using = LongToStringSerialize.class)
	private Long eventTargetId;
	/**
	 * @desc 事件标签
	 */
	private String eventTargetName;
	/**
	 * @desc 标签分类编码
    @value link、value、char
	 */
	private String eventTargetGrope;
	/**
	 * @desc 事件标签编码
	 */
	private String eventTargetCode;


}
