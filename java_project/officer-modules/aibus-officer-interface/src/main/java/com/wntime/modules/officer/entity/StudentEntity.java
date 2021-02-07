package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 学生信息表
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_student")
public class StudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 基础信息id
	 */
	private Long basicId;
	/**
	 * @desc 第一监护人id
	 */
	private Long guardianId;
	/**
	 * @desc 班级id
	 */
	private Long classesId;
	/**
	 * @desc 学校id
	 */
	private Long schoolId;
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
