package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 公交车站表
 * 
 * @date 2020-08-25 14:00:25
 */
@Data
@TableName("info_bus_station")
public class InfoBusStationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "bus_station_id", type = IdType.ID_WORKER)
	private Long busStationId;

	@NotNull(message = "区域ID不能为空")
	private Long areaId;

	@NotNull(message = "学校ID不能为空")
	private Long schoolId;

	@TableField(exist = false)
	private String areaName;
	/**
	 * @desc 公交车站名称
	 */
	@NotNull(message = "车站名称不能为空")
	@Size(min=0,max = 20,message = "车站名称不能超过20个字符")
	private String busStationName;
	/**
	 * @desc 公交车站编码
	 */
//	@NotNull(message = "车站编码不能为空")
//	@Size(min=0,max = 20,message = "车站编码不能超过20个字符")
	private String busStationCode;
	/**
	 * @desc 经度
	 */
	@NotNull(message = "经度不能为空")
	private Double busStationLongitude;
	/**
	 * @desc 纬度
	 */
	@NotNull(message = "纬度不能为空")
	private Double busStationLatitude;
	/**
	 * @desc 偏差(米)
	 */
	@NotNull(message = "偏差(米)为空")
	@Positive(message = "偏差(米)必须大于0" )
	private Integer busStationDeviation;
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


	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;

	@TableField(exist = false)
	private String companyLineName;

}
