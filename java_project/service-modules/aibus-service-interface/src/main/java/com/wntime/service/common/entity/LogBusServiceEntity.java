package com.wntime.service.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 营运日志表
 * 
 * @date 2020-08-25 14:00:25
 */
@Data
@TableName("log_bus_service")
public class LogBusServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "bus_service_id", type = IdType.ID_WORKER)
	private Long busServiceId;

	/**
	 * @desc 公交车Id
	 */
	@TableField(exist = false)
	private Long busId;

	private Long itineraryId;

	@TableField(exist = false)
	private String vinCode;

	@TableField(exist = false)
	private String plateCode;
	/**
	 * @desc 公交线路Id
	 */
	@TableField(exist = false)
	private Long companyLineId;

	@TableField(exist = false)
	private String companyLineName;

	/**
	 * @desc 司机特征码
	 */
	private String driverCode;
	/**
	 * @desc 司机照片
	 */
	private String driverImage;
	/**
	 * @desc 经度
	 */
	private Double companyLongitude;
	/**
	 * @desc 纬度
	 */
	private Double companytLatitude;
	/**
	 * @desc 停靠站Id
	 */
	private Long busStationId;

	@TableField(exist = false)
	private String busStationName;
	/**
	 * @desc 下一站Id
	 */
	private Long nextStationId;

	private Integer nextStationArriveMin;

	@TableField(exist = false)
	private String nextStationName;
	/**
	 * @desc 上车人数
	 */
	private Integer busGetOn;
	/**
	 * @desc 下车人数
	 */
	private Integer busGetOff;
	/**
	 * @desc 乘车人数
	 */
	private Integer busKeepRide;
	/**
	 * @desc 儿童人数
	 */
	private Integer childrenNum;
	/**
	 * @desc 成年人数
	 */
	private Integer adultNum;
	/**
	 * @desc 老年人数
	 */
	private Integer oldMun;
	/**
	 * @desc 儿童人数
	 */
	private Integer childrenTotal;
	/**
	 * @desc 成年人数
	 */
	private Integer adultTotal;
	/**
	 * @desc 老年人数
	 */
	private Integer oldTotal;
	/**
	 * @desc 男性累计
	 */
	private Integer maleTotal;
	/**
	 * @desc 女性累计
	 */
	private Integer femaleTotal;
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

	private String arrivalTime;
	private String leaveTime;
}
