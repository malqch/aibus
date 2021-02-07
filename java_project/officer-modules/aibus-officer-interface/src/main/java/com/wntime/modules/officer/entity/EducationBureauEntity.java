package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 教育局
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_education_bureau")
public class EducationBureauEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 单位名称
	 */
	private String orgName;
	/**
	 * @desc 网站地址
	 */
	private String website;
	/**
	 * @desc 办公地址
	 */
	private String address;
	/**
	 * @desc 创建人
	 */
	private String createUserId;
	/**
	 * @desc 创建时间
	 */
	private Date createDt;
	/**
	 * @desc 更新人
	 */
	private String modifyUserId;
	/**
	 * @desc 更新时间
	 */
	private Date modifyDt;
	private String isDeleted;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;
}
