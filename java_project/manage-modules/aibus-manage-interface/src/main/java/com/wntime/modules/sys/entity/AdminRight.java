package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.entity.BaseEntity;
import com.wntime.util.LongToStringSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TableName("admin_right")
public class AdminRight extends BaseEntity{

  private static final long serialVersionUID = -8566273597134519705L;

  @TableId(value = "right_id", type = IdType.ID_WORKER)
  @JsonSerialize(using = LongToStringSerialize.class)
  private Long rightId;
  @TableField(exist=false)
  private List<?> list;
  private String name;

  @JsonSerialize(using = LongToStringSerialize.class)
  private Long parentId;
  @TableField(exist=false)
  private String parentName;
  private String description;
  private String iconCss;
  private Long sort;
  private Integer type;
  private String endpointPath;
  private String authDetails;
  private String isEnabled;
  private String isDeleted;

  public Long getRightId() {
    return rightId;
  }

  public void setRightId(Long rightId) {
    this.rightId = rightId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIconCss() {
    return iconCss;
  }

  public void setIconCss(String iconCss) {
    this.iconCss = iconCss;
  }

  public Long getSort() {
    return sort;
  }

  public void setSort(Long sort) {
    this.sort = sort;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getEndpointPath() {
    return endpointPath;
  }

  public void setEndpointPath(String endpointPath) {
    this.endpointPath = endpointPath;
  }

  public String getAuthDetails() {
    return authDetails;
  }

  public void setAuthDetails(String authDetails) {
    this.authDetails = authDetails;
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
  public List<?> getList() {
    return list;
  }

  public void setList(List<?> list) {
    this.list = list;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
}
