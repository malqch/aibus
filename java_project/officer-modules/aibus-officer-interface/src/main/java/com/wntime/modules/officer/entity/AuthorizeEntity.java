package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 授权人
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_authorize")
public class AuthorizeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 基本信息主键
	 */
	private Long basicId;
	/**
	 * @desc 学生id
	 */
	private Long studentId;
	/**
	 * @desc 授权状态
	 */
	private String authStatus;
	/**
	 * @desc 授权序号
	 */
	private Integer authNumber;
	/**
	 * @desc 与学生关系
	 */
	private String relationStudent;
	/**
	 * @desc 手机号
	 */
	private String mobileNumber;
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

	private String isDeleted;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;
}
