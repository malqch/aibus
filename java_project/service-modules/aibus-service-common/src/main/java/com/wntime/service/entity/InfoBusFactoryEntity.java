package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 公交车厂表
 * 
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_bus_factory")
public class InfoBusFactoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "factory_id", type = IdType.ID_WORKER)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long factoryId;
	/**
	 * @desc 区域主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long areaId;

	//区域名称
	@TableField(exist = false)
	private String areaName;

	/**
	 * @desc 公交车厂名称
	 */
	@NotNull(message = "车厂名称不能为空")
	@Size(min=0,max = 64,message = "车厂名称不能超过64个字符")
	private String factoryName;
	/**
	 * @desc 公交车厂招牌
	 */
	private String factorySnapshot;

	@TableField(exist = false)
	private String factorySnapshotUrl;

	@TableField(exist = false)
	@JsonIgnore
	private MultipartFile factorySnapshotFile;
	/**
	 * @desc 统一信用代码
	 */
	@NotNull(message = "统一信用代码不能为空")
	@Size(min=0,max = 64,message = "统一信用代码不能超过64个字符")
	private String factoryCode;
	/**
	 * @desc 经营范围
	 */
	@NotNull(message = "经营范围不能为空")
	@Size(min=0,max = 512,message = "经营范围不能超过512个字符")
	private String factoryBusinessScope;
	/**
	 * @desc 车厂地址
	 */
	@NotNull(message = "车厂地址不能为空")
	@Size(min=0,max = 128,message = "车厂地址不能超过128个字符")
	private String factoryAddress;
	/**
	 * @desc 经度
	 */
	private Double factoryLongitude;
	/**
	 * @desc 纬度
	 */
	private Double factoryLatitude;
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

}
