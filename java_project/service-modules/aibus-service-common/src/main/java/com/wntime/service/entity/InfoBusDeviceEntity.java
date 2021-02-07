package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.util.DateUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc AI设备表
 * 
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_bus_device")
public class InfoBusDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(value = "bus_device_id", type = IdType.ID_WORKER)
	private Long busDeviceId;
	/**
	 * @desc 设备类型Id
	 */
	@NotNull(message = "设备类型不能为空")
	private Long deviceTypeId;

	//公交车
	@TableField(exist = false)
	private String deviceTypeName;

	/**
	 * @desc 公交车Id
	 */
	@NotNull(message = "所属公交车不能为空")
	private Long busId;

	//公交车
	@TableField(exist = false)
	private String busName;

	//公交车型
	@TableField(exist = false)
	private String busTypeName;

	//公交车牌
	@TableField(exist = false)
	private String plateCode;

	//公交VIN
	@TableField(exist = false)
	private String vinCode;

	/**
	 * @desc 设备名称
	 */
	@NotBlank(message = "设备名称不能为空")
	@Size(min = 0,max = 32,message = "设备名称长度应小于32")
	private String deviceName;
	/**
	 * @desc 设备编号
	 */
	@NotBlank(message = "设备编号不能为空")
	@Size(min = 0,max = 64,message = "设备编号长度应小于64")
	private String deviceCode;

	@Size(min = 0,max = 64,message = "设备描述编码长度应小于64")
	private String deviceDescCode;
	/**
	 * @desc 设备状态
    @value 连接中，在线，离线
	 */
	@NotNull(message = "设备状态不能为空")
	private Integer deviceStatus;
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

	public void init(){
		this.isDeleted= Constant.Deleted.UNDELETED.getValue();
		this.isEnabled=Constant.Enabled.ENABLE.getValue();
		this.createdDate= DateUtils.getTimestamp();
		this.modifiedDate=this.createdDate;
	}
}
