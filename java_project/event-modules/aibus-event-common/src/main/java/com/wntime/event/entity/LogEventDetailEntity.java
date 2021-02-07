package com.wntime.event.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 事件日志表
 * 
 * @date 2020-08-25 13:34:24
 */
@Data
@TableName("log_event_detail")
public class LogEventDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "event_detail_id", type = IdType.ID_WORKER)
	private Long eventDetailId;
	/**
	 * @desc 事件采集Id
	 */
	private Long collectEventId;
	/**
	 * @desc 设备Id
	 */
	private Long busDeviceId;

	/**
	 * 行驶日志Id
	 */
	private Long busDriveId;
	/**
	 * @desc 事件类型Id
	 */
	private Long eventTypeId;
	/**
	 * @desc 事件标签Id
	 */
	private Long eventTargetId;
	/**
	 * @desc 事件级别Id
	 */
	private Long eventLevelId;
	/**
	 * @desc 设备类型Id
	 */
	private Long deviceTypeId;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

	private Long itineraryId;

}
