package com.wntime.service.vo;

import lombok.Getter;
import lombok.Setter;


/**
 * @desc 设备类型
 * 
 * @date 2020-08-25 14:28:17
 */
@Setter
@Getter
public class InfoDeviceTypeVo{

	/**
	 * @desc 主键
	 */
	private Long deviceTypeId;
	/**
	 * @desc 设备类型名称
	 */
	private String deviceTypeName;
	/**
	 * @desc 设备类型编码
	 */
	private String deviceTypeCode;

}
