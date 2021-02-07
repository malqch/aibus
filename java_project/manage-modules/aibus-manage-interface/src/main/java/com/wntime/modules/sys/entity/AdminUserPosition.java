package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;


@TableName("admin_user_position")
public class AdminUserPosition extends BaseEntity {

  private static final long serialVersionUID = 4187953696597652066L;

  @TableId(value = "user_position_id",type = IdType.ID_WORKER)
  private Long userPositionId;
  private Long userId;
  private Long positionId;
  private String isDeleted;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getPositionId() {
    return positionId;
  }

  public void setPositionId(Long positionId) {
    this.positionId = positionId;
  }


  public String getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(String isDeleted) {
    this.isDeleted = isDeleted;
  }

  public Long getUserPositionId() {
    return userPositionId;
  }

  public void setUserPositionId(Long userPositionId) {
    this.userPositionId = userPositionId;
  }
}
