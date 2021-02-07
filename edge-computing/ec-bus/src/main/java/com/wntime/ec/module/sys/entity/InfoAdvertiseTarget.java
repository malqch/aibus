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
* @创建时间：2020-11-06 03:59:42
* @文件描述：info_advertise_target 广告标签表
*/
@ApiModel(description = "广告标签表" )
@Data
public class InfoAdvertiseTarget implements Serializable {
   private static final long serialVersionUID = 1L;

	/**
	* id
	*/ 
	@Null(message ="ID必须为空",groups={AddGroup.class})
	@NotNull(message ="ID不能为空",groups={UpdateGroup.class})
	@ApiModelProperty(value="id",name="advertiseTargetId",required=true) 
	protected Long advertiseTargetId;

	/**
	* 广告标签
	*/ 
	@Size(max=100 ,message ="广告标签最多100个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告标签",name="advertiseTargetName",required=false) 
	protected String advertiseTargetName;

	/**
	* 广告分类编码 link、value、char
	*/ 
	@Size(max=100 ,message ="广告分类编码 link、value、char最多100个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告分类编码 link、value、char",name="advertiseTargetGrope",required=false) 
	protected String advertiseTargetGrope;

	/**
	* 广告标签编码
	*/ 
	@Size(max=100 ,message ="广告标签编码最多100个字符",groups=AddGroup.class) 
	@ApiModelProperty(value="广告标签编码",name="advertiseTargetCode",required=false) 
	protected String advertiseTargetCode;




}
