package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 班级信息
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Data
@TableName("info_classes")
public class ClassesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 班级名称
	 */
	private String className;
	/**
	 * @desc 班主任姓名
	 */
	private String teacherName;
	/**
	 * @desc 班主任电话
	 */
	private String teacherMobileNumber;
	/**
	 * @desc 学校编码
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

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;

	private String isDeleted;

	@TableField(exist = false)
	private Integer studentCout;
	@TableField(exist = false)
	private Integer studentTakePhotoNullCout;
	@TableField(exist = false)
	private Integer guardianTakePhotoNullCout;
}
