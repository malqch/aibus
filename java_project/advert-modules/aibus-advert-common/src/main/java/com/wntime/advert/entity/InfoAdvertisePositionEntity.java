package com.wntime.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * ${comments}
 * 
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Data
@TableName("info_advertise_position")
public class InfoAdvertisePositionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long advertisePositionId;
	/**
	 * @desc 广告位描述
	 */
	@NotBlank(message = "描述不能为空")
	@Size(min = 0,max = 100,message = "描述长度应小于100")
	private String positionDesc;
	/**
	 * @desc 广告位编码
	 */
	@NotBlank(message = "编码不能为空")
	@Size(min = 0,max = 34,message = "编码长度应小于34")
	private String positionCode;
	/**
	 * @desc 广告位分类
	 */
	@NotBlank(message = "分类不能为空")
	@Size(min = 0,max = 34,message = "描述长度应小于34")
	private String positionGroup;
	/**
	 * @desc 像素高
	 */
	@NotNull(message = "像素高不能为空")
	@Positive(message = "像素高必须大于0" )
	private Double pixelHeight;
	/**
	 * @desc 像素宽
	 */
	@NotNull(message = "像素宽不能为空")
	@Positive(message = "像素宽必须大于0" )
	private Double pixelWidth;
	/**
	 * @desc 屏幕高
	 */
	@NotNull(message = "屏幕高不能为空")
	@Positive(message = "屏幕高必须大于0" )
	private Double screenHeight;
	/**
	 * @desc 屏幕宽
	 */
	@NotNull(message = "屏幕宽不能为空")
	@Positive(message = "屏幕宽必须大于0" )
	private Double screenWidth;
	/**
	 * @desc 素材类型
@values 0 单图片；1 单视频；2 全可以
	 */
	@NotNull(message = "素材类型不能为空")
	private Integer advertiseType;
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
	private String createdUserName;

	@TableField(exist = false)
	private String modifiedUserName;

}
