package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 校车安全员
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_safety_officer")
public class SafetyOfficerEntity implements Serializable {
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
	 * @desc 无犯罪记录证明照片
	 */
	private String noCriminalRecordPhoto;
	/**
	 * @desc 手机号
	 */
	private String mobileNumber;
	/**
	 * 系统登录账号id
	 */
	private Long loginUserId;
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

//	is_primary

	private String isPrimary;


	@TableField(exist = false)
	private String safetyOfficerName;
	private Integer isDeleted;



	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;

}
