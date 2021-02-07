package com.wntime.customer.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 公交公司表
 * 
 * @date 2020-08-25 14:04:05
 */
@Data
public class InfoBusCompanyVo  {

	/**
	 * @desc 主键
	 */
	private Long companyId;
	/**
	 * @desc 区域主键
	 */
	private Long areaId;

	private String areaName;
	/**
	 * @desc 公交公司名称
	 */
	private String companyName;
	/**
	 * @desc 公交公司图标
	 */
	private String companySnapshot;
	/**
	 * @desc 统一信用代码
	 */
	private String companyCode;
	/**
	 * @desc 经营范围
	 */
	private String companyScope;
	/**
	 * @desc 公司地址
	 */
	private String companyAddress;
	/**
	 * @desc 联系人
	 */
	private String companyPerson;
	/**
	 * @desc 电话
	 */
	private String companyPhone;
	/**
	 * @desc 邮箱
	 */
	private String companyEmail;
	/**
	 * @desc 经度
	 */
	private Double companyLongitude;
	/**
	 * @desc 纬度
	 */
	private Double companytLatitude;
	/**
	 * @desc 是否删除
	 */
	private Integer isDeleted;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;

	/**
	 * 上级公司id
	 */
	private Long parentCompanyId;

	/**
	 * 是否是集团  1 是 0 否
	 */
	private int isClique;

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

	private Long orderCount;

	private Long deliveryCount;

	private String createUserName;

	private String modifiedUserName;

}
