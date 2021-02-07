package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;


@TableName("admin_user_role")
public class AdminUserRole extends BaseEntity{

  private static final long serialVersionUID = 8015472336062570760L;

  @TableId(value = "id", type = IdType.ID_WORKER)
  private Long id;
  private Long userId;
  private Long roleId;
  private String isDeleted;

  private Long objectId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(String isDeleted) {
    this.isDeleted = isDeleted;
  }

  public Long getObjectId() {
    return objectId;
  }

  public void setObjectId(Long objectId) {
    this.objectId = objectId;
  }
}
