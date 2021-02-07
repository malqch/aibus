package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.entity.BaseEntity;

import java.sql.Date;

@TableName("admin_auth_object")
public class AdminAuthObject extends BaseEntity {

  @TableId(value = "auth_object_id", type = IdType.ID_WORKER)
  private Long authObjectId;

  private long positionAuthId;
  private long busiObjectId;
  private long userPositionId;
  private String isDeleted;

  public long getUserPositionId() {
    return userPositionId;
  }

  public void setUserPositionId(long userPositionId) {
    this.userPositionId = userPositionId;
  }

  public Long getAuthObjectId() {
    return authObjectId;
  }

  public void setAuthObjectId(Long authObjectId) {
    this.authObjectId = authObjectId;
  }

  public long getPositionAuthId() {
    return positionAuthId;
  }

  public void setPositionAuthId(long positionAuthId) {
    this.positionAuthId = positionAuthId;
  }

  public long getBusiObjectId() {
    return busiObjectId;
  }

  public void setBusiObjectId(long busiObjectId) {
    this.busiObjectId = busiObjectId;
  }

  public String getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(String isDeleted) {
    this.isDeleted = isDeleted;
  }
}
