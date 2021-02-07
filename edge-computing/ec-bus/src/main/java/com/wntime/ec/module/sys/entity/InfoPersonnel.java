package com.wntime.ec.module.sys.entity;

import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

//import org.hibernate.validator.constraints.NotEmpty;

/**
* @创建时间：2020-11-06 03:59:42
*/
@Data
public class InfoPersonnel implements Serializable {
   	private static final long serialVersionUID = 1L;

	protected Long uniqueIdentity;
   	protected Integer sublibType;
	protected String name;
	protected String idNo;
	protected String img;
	protected String isDeleted;

	@Override
	public String toString() {
		return "InfoPersonnel{" +
				"uniqueIdentity=" + uniqueIdentity +
				", sublibType=" + sublibType +
				", name='" + name + '\'' +
				", idNo='" + idNo + '\'' +
				", isDeleted='" + isDeleted + '\'' +
				'}';
	}
}
