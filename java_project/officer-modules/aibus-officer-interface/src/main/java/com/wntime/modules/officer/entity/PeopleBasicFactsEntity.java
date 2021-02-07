package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 人员基础信息
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_people_basic_facts")
public class PeopleBasicFactsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 身份证号
	 */
	private String idNo;
	/**
	 * @desc 姓名
	 */
	private String fullName;
	/**
	 * @desc 性别
	 */
	private String sex;
	/**
	 * @desc 年龄
	 */
	private Integer age;
	/**
	 * @desc 身份验证照片
	 */
	private String takePhoto;
	/**
	 * @desc 居住地址
	 */
	private String residentialAddress;
	/**
	 * @desc 创建人
	 */
	private Long createUserId;
	/**
	 * @desc 创建时间
	 */
	private Date createDt;
	/**
	 * @desc 更新人
	 */
	private Long modifyUserId;
	/**
	 * @desc 更新时间
	 */
	private Date modifyDt;

	private String category;
	private String isDeleted;

	@TableField(exist = false)
	private Integer sublibType;

	@TableField(exist = false)
	private String name;

	@TableField(exist = false)
	private String img;

	@TableField(exist = false)
	private Long uniqueIdentity;
}
