package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 区域表
 * 
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_area")
public class InfoAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "area_id", type = IdType.ID_WORKER)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long areaId;
	/**
	 * @desc 区域编码
    @logic 6为身份证地址编码+6位邮政编码
	 */
	private Integer areaCode;
	/**
	 * @desc 区域名称
	 */
	private String areaName;
	/**
	 * @desc 区域简称
	 */
	private String areaAlias;
	/**
	 * @desc 上级区域
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentAreaId;

	@TableField(exist = false)
	private String parentAreaName;

	/**
	 * @desc 区域级别
    @default 1
    @logic 上级级别+1
	 */
	private Integer areaLevel;
	/**
	 * @desc 排序
	 */
	private Integer areaSort;
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
	private Boolean hasChild;

	@TableField(exist = false)
	private Boolean hasParent;

}
