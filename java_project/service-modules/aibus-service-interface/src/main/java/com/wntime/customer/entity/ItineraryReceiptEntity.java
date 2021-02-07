package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author buxl
 * @email 
 * @date 2021-01-21 16:26:42
 */
@Data
@TableName("itinerary_receipt")
public class ItineraryReceiptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 行程回单
	 */
	@TableId
	private Long itineraryId;
	/**
	 * 营运计划id
	 */
	private Long planServiceId;
	/**
	 * 行程时间
	 */
	private String itineraryDate;
	/**
	 * 行程状态
	 */
	private String itineraryStatus;
	/**
	 * 是否为备选线路
	 */
	private String isBackup;
	/**
	 * 上报状态
	 */
	private String reportStatus;
	/**
	 * 上报方式
	 */
	private String reportWay;
	/**
	 * 预计发车时间
	 */
	private Date predictDepartTime;
	/**
	 * 车辆id
	 */
	private Long busId;

	/**
	 * 车辆状态
	 */
	private String busStatus;

	/**
	 * 司机id
	 */
	private Long driverId;
	/**
	 * 司机姓名
	 */
	private String driverFullName;
	/**
	 * 司机岗位
	 */
	private String driverPosition;
	/**
	 * 司机体温
	 */
	private Double driverBodyTemperature;
	/**
	 * 司机配戴口罩
	 */
	private String driverWearMask;
	/**
	 * 司机手部消毒
	 */
	private String driverHandDisinfection;
	/**
	 * 行车违纪次数
	 */
	private Integer drivingViolationTotal;
	/**
	 * 安全员id
	 */
	private Long safetyOfficerId;
	/**
	 * 安全员姓名
	 */
	private String safetyOfficerFullName;
	/**
	 * 安全员体温
	 */
	private Double safetyOfficerBodyTemperature;
	/**
	 * 安全员戴口罩
	 */
	private String safetyOfficerWearMask;
	/**
	 * 安全员手消毒
	 */
	private String safetyOfficerHandDisinfectio;
	/**
	 * 车内消毒
	 */
	private String busDisinfection;
	/**
	 * 未授权告警
	 */
	private Integer noAuthorizeWarn;
	/**
	 * 行程开始时间
	 */
	private Timestamp itineraryStartTime;
	/**
	 * 行程结束时间
	 */
	private Timestamp itineraryEndTime;
	/**
	 * 行程方向
	 */
	private String itineraryDirection;
	/**
	 * 应载人数
	 */
	private Integer takeTotal;
	/**
	 * 实载人数
	 */
	private Integer loadTotal;
	/**
	 * 未上车人数
	 */
	private Integer nonTotal;
	/**
	 * 请假人数
	 */
	private Integer leaveTotal;
	/**
	 * 已下车人数
	 */
	private Integer offBusTotal;
	/**
	 * 体温异常人数
	 */
	private Integer bodyTemperatureAbnormalTotal;
	/**
	 * 未戴口罩人数
	 */
	private Integer notWearMaskTotal;
	/**
	 * 未手部消毒人数
	 */
	private Integer notHandDisinfectionTotal;
	/**
	 * 违纪人数
	 */
	private Integer studentViolationTotal;
	/**
	 * 违纪次数
	 */
	private Integer studentViolationCount;
	/**
	 * 车速记录
	 */
	private BigDecimal maxSpeed;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	private Timestamp createDt;
	/**
	 * 更新人
	 */
	private Long modifyUserId;
	/**
	 * 更新时间
	 */
	private Timestamp modifyDt;

	/**
	 * 安全员岗位
	 */
	private String safetyOfficerPosition;

	/**
	 * 首发站到站时间
	 */
	@TableField(exist = false)
	private String expectedArrivalTime;

	@TableField(exist = false)
	private Long companyLineId;

	@TableField(exist = false)
	private Long busStationId;

	@TableField(exist = false)
	private int stationOrder;

	@TableField(exist = false)
	private Double busStationLongitude;

	@TableField(exist = false)
	private Double busStationLatitude;

	@TableField(exist = false)
	private Long firstStationId;

	@TableField(exist = false)
	private Double firstStationLongitude;

	@TableField(exist = false)
	private Double firstStationLatitude;

	@TableField(exist = false)
	private Integer studentCount;

}
