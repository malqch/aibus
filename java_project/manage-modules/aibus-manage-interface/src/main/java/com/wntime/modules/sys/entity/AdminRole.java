package com.wntime.modules.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wntime.common.validator.group.UpdateGroup;
import com.wntime.entity.BaseEntity;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@TableName("admin_role")
public class AdminRole{


  private static final long serialVersionUID = -5074994436382646183L;
  @TableId(value = "role_id", type = IdType.ID_WORKER)
  @JsonSerialize(using = ToStringSerializer.class)
  private Long roleId;

  @TableField(exist=false)
  private List<String> rightIdList;

  @NotBlank(message="角色名称不能为空")
  private String name;
  private String description;
  private String isEnabled;
  private String isDeleted;
  private Long createUserId;

  @TableField(exist=false)
  private Long objectId;

  private Timestamp createDt;

  private Long modifyUserId;

  private Timestamp modifyDt;

  private String roleCode;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(String isDeleted) {
    this.isDeleted = isDeleted;
  }
  public List<String> getRightIdList() {
    return rightIdList;
  }

  public void setRightIdList(List<String> rightIdList) {
    this.rightIdList = rightIdList;
  }


  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Timestamp getCreateDt() {
    return createDt;
  }
  public void setCreateDt(Timestamp createDt) {
    this.createDt = createDt;
  }

  public Long getModifyUserId() {
    return modifyUserId;
  }

  public void setModifyUserId(Long modifyUserId) {
    this.modifyUserId = modifyUserId;
  }

  public Timestamp getModifyDt() {
    return modifyDt;
  }

  public void setModifyDt(Timestamp modifyDt) {
    this.modifyDt = modifyDt;
  }

  public Long getObjectId() {
    return objectId;
  }

  public void setObjectId(Long objectId) {
    this.objectId = objectId;
  }

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }
}
