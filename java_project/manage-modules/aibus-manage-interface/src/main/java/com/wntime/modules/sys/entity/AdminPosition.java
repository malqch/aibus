package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wntime.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@TableName("admin_position")
public class AdminPosition extends BaseEntity{
  private static final long serialVersionUID = 8985207677606564440L;
  @TableId(type = IdType.ID_WORKER)
  private Long positionId;

  @NotBlank(message="岗位名称不能为空")
  private String name;
  private String description;

  private String systemAuth;//系统授权范围manage\monitorz\monitorj\webapp\edge

  private String isEnabled;
  private String isDeleted;

  public Long getPositionId() {
    return positionId;
  }

  public void setPositionId(Long positionId) {
    this.positionId = positionId;
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

  public String getSystemAuth() {
    return systemAuth;
  }

  public void setSystemAuth(String systemAuth) {
    this.systemAuth = systemAuth;
  }
}
