package com.wntime.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @desc 广告标签表
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Data
@TableName("info_advertise_target")
public class InfoAdvertiseTargetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long advertiseTargetId;
	/**
	 * @desc 广告标签
	 */
	@NotBlank(message = "标签名称不能为空")
	@Size(min = 0,max = 20,message = "标签名称长度应小于20")
	private String advertiseTargetName;
	/**
	 * @desc 广告分类编码
@value link、value、char
	 */
	@NotBlank(message = "标签分类不能为空")
	@Size(min = 0,max = 32,message = "标签分类长度应小于32")
	private String advertiseTargetGrope;
	/**
	 * @desc 广告标签编码
	 */
	@NotBlank(message = "标签编码不能为空")
	@Size(min = 0,max = 32,message = "标签编码长度应小于32")
	private String advertiseTargetCode;
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

	@TableField(exist = false)
	private String createdUserName;

	@TableField(exist = false)
	private String modifiedUserName;
	/**
	 * @desc 修改时间
	 */
	private Timestamp modifiedDate;

}
