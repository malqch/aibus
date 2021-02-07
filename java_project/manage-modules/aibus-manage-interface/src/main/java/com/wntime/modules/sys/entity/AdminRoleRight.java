package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.entity.BaseEntity;
import com.wntime.util.LongToStringSerialize;

import java.io.Serializable;
import java.util.Date;

@TableName("admin_role_right")
public class AdminRoleRight extends BaseEntity{


  private static final long serialVersionUID = -754760231860925671L;

@TableId(value = "id", type = IdType.ID_WORKER)
@JsonSerialize(using = LongToStringSerialize.class)
  private Long id;
  private Long roleId;
  private Long rightId;
  private String isDeleted;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public Long getRightId() {
    return rightId;
  }

  public void setRightId(Long rightId) {
    this.rightId = rightId;
  }


  public String getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(String isDeleted) {
    this.isDeleted = isDeleted;
  }
}
