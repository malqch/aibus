package com.wntime.ec.module.sys.entity ;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
* @创建时间：2020-11-06 03:59:11
* @文件描述：info_advertise_position 广告位表
*/
@ApiModel(description = "广告位表" )
@Data
public class InfoAdvertisePosition implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="advertisePositionId",required=true) 
	protected Long advertisePositionId;

	/**
	* 广告位描述
	*/ 
	@Size(max=200 ,message ="广告位描述最多200个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告位描述",name="positionDesc",required=false) 
	protected String positionDesc;

	/**
	* 广告位编码
	*/ 
	@Size(max=100 ,message ="广告位编码最多100个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告位编码",name="positionCode",required=false) 
	protected String positionCode;

	/**
	* 广告位分类
	*/ 
	@Size(max=100 ,message ="广告位分类最多100个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告位分类",name="positionGroup",required=false) 
	protected String positionGroup;

	/**
	* 像素高
	*/ 
	@ApiModelProperty(value="像素高",name="pixelHeight",required=false) 
	protected Integer pixelHeight;

	/**
	* 像素宽
	*/ 
	@ApiModelProperty(value="像素宽",name="pixelWidth",required=false) 
	protected Integer pixelWidth;

	/**
	* 屏幕高
	*/ 
	@ApiModelProperty(value="屏幕高",name="screenHeight",required=false) 
	protected Integer screenHeight;

	/**
	* 屏幕宽
	*/ 
	@ApiModelProperty(value="屏幕宽",name="screenWidth",required=false) 
	protected Integer screenWidth;

	/**
	* 素材类型 0 单图片；1 单视频；2 全可以
	*/ 
	@ApiModelProperty(value="素材类型 0 单图片；1 单视频；2 全可以",name="advertiseType",required=false) 
	protected Integer advertiseType;




}
