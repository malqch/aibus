package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 线路车站表
 * 
 * @date 2020-08-25 14:23:25
 */
@Data
@TableName("info_line_station")
public class InfoLineStationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "line_station_id", type = IdType.ID_WORKER)
	private Long lineStationId;
	/**
	 * @desc 公交线路Id
	 */
	private Long companyLineId;
	/**
	 * @desc 公交车站Id
	 */
	private Long busStationId;
	/**
	 * @desc 序号
	 */
	private Integer stationOrder;
	/**
	 * @desc 车站类型
	 */
	private Long stationType;
	/**
	 * @desc 是否删除
	 */
	private Integer isDeleted;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;
	/**
	 * @desc 创建人
	 */
	private Long createdBy;
	/**
	 * @desc 创建时间
	 */
	private Timestamp createdDate;
	/**
	 * @desc 修改人
	 */
	private Long modifiedBy;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
