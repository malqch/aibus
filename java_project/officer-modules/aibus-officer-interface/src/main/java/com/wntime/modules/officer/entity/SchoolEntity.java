package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 学校信息
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_school")
public class SchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 学校名称
	 */
	private String schoolName;
	/**
	 * $column.comments
	 */
	private Long educationBureauId;
	/**
	 * @desc 备注
	 */
	private String remark;
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
