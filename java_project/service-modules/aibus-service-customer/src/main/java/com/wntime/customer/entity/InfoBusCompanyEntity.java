package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 公交公司表
 * 
 * @date 2020-08-25 14:04:05
 */
@Data
@TableName("info_bus_company")
public class InfoBusCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "company_id", type = IdType.ID_WORKER)
	private Long companyId;
	/**
	 * @desc 区域主键
	 */
	@NotNull(message = "所属区域不能为空")
	private Long areaId;

	@NotNull(message = "学校id不能为空")
	private Long schoolId;

	/**
	 * @desc 公交公司名称
	 */
	@NotBlank(message = "公交公司名称不能为空")
	@Size(min = 0,max = 64,message = "公交公司名称长度应小于64")
	private String companyName;
	/**
	 * @desc 公交公司图标
	 */
	//@NotBlank(message = "公交公司图标不能为空")
	@Size(min = 0,max = 256,message = "公交公司图标长度应小于256")
	private String companySnapshot;
	@TableField(exist = false)
	@JsonIgnore
	private MultipartFile companySnapshotFile;
	/**
	 * @desc 统一信用代码
	 */
	@NotBlank(message = "统一信用代码不能为空")
	@Size(min = 0,max = 64,message = "统一信用代码长度应小于64")
	private String companyCode;
	/**
	 * @desc 经营范围
	 */
	@NotBlank(message = "经营范围不能为空")
	@Size(min = 0,max = 512,message = "经营范围长度应小于512")
	private String companyScope;
	/**
	 * @desc 公司地址
	 */
	@NotBlank(message = "公司地址不能为空")
	@Size(min = 0,max = 128,message = "公司地址长度应小于128")
	private String companyAddress;
	/**
	 * @desc 联系人
	 */
	@NotBlank(message = "联系人不能为空")
	@Size(min = 0,max = 64,message = "联系人长度应小于64")
	private String companyPerson;
	/**
	 * @desc 电话
	 */
	@NotBlank(message = "联系电话不能为空")
	@Pattern(regexp = "^(([1][0-9]{10})|((\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}))$",message = "联系电话不合法")
	private String companyPhone;
	/**
	 * @desc 邮箱
	 */
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式不合法")
	private String companyEmail;
	/**
	 * @desc 经度
	 */
	@NotNull(message = "经度不能为空")
	private Double companyLongitude;
	/**
	 * @desc 纬度
	 */
	@NotNull(message = "纬度不能为空")
	private Double companytLatitude;
	/**
	 * 上级公司id
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Long parentCompanyId;

	/**
	 * 是否是集团  1 是 0 否
	 */
	private int isClique;
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

}
