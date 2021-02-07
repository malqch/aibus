package com.wntime.service.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * @desc 公交车表
 * 
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_bus")
public class InfoBusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "bus_id", type = IdType.ID_WORKER)
	private Long busId;
	/**
	 * @desc 公交车厂Id
	 */
//	@NotNull(message = "公交车厂不能为空")
	private Long factoryId;

	//公交车厂名称
	@TableField(exist = false)
	private String factoryName;


	private Long companyId;
	private Long schoolId;

	@TableField(exist = false)
	private String companyName;

	/**
	 * @desc 公交车类型
	 */
	@NotNull(message = "公交车类型不能为空")
	private Long busTypeId;

	//公交车类型名称
	@TableField(exist = false)
	private String busTypeName;

	/**
	 * @desc 车牌照号
	 */
	@NotBlank(message = "车牌号码不能为空")
	private String plateCode;

	/**
	 * @desc 车辆编号
	 */
	@NotBlank(message = "车辆编号不能为空")
	private String busCode;

	/**
	 * @desc VIN编号
	 */
	@NotBlank(message = "vin码不能为空")
	@Size(min = 0,max = 64,message = "vin码长度应小于64")
	private String vinCode;
	/**
	 * @desc 是否删除
	 */
	private Integer isDeleted;
	/**
	 * @desc 是否启用
	 */
	private Integer isEnabled;

	/**
	 * 车辆登记日期
	 */
	@TableField(exist = false)
	private String registrationDateStr;
	private Date registrationDate;

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
	/**
	 * @desc 车辆状态
	 */
//	@NotNull(message = "车辆状态不能为空")
	private Long busStatus;
//	@NotNull(message = "运行状态不能为空")
	private int runStatus;
	//车辆状态说明
	@TableField(exist = false
	)
	private String busStatusDesc;

}
