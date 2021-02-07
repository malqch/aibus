package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 驾驶员信息表
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_driver")
public class DriverEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 基本信息主键
	 */
	private Long basicId;
	/**
	 * 驾驶证件号
	 */
	private String drivingLicenseNo;
	/**
	 * 初次领证时间
	 */
	private Date firstIssueDate;
	/**
	 * 驾驶证类型
	 */
	private String drivingLicenseClass;
	/**
	 * 手机号
	 */
	private String mobileNumber;
	/**
	 * 驾驶证照片
	 */
	private String driversLicensePhoto;
	/**
	 * 驾驶证有效期限起始时间
	 */
	private Date validPeriodStart;
	/**
	 * 驾驶证有效期限结束时间
	 */
	private Date validPeriodEnd;
	/**
	 * 无犯罪记录证明照片
	 */
	private String noCriminalRecordPhoto;
	/**
	 * 系统登录用户id
	 */
	private Long loginUserId;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	private Date createDt;
	/**
	 * 更新人
	 */
	private Long modifyUserId;
	/**
	 * 更新时间
	 */
	private Date modifyDt;

	private String isPrimary;

	@TableField(exist = false)
	private String driverName;

	private String isDeleted;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;
}
