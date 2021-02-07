package com.wntime.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.entity.BaseEntity;
import com.wntime.util.LongToStringSerialize;


@TableName("admin_operation_log")
public class AdminOperationLog extends BaseEntity {
  private static final long serialVersionUID = -3381804529538090910L;

  @TableId(type = IdType.ID_WORKER)
  @JsonSerialize(using = LongToStringSerialize.class)
  private Long operationLogId;
  private String username;
  private String  operation;
  private String requestMethod;
  private String requestParams;
  private String requestAddress;
  private Long executedTime;

  public Long getOperationLogId() {
    return operationLogId;
  }

  public void setOperationLogId(Long operationLogId) {
    this.operationLogId = operationLogId;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getRequestParams() {
    return requestParams;
  }

  public void setRequestParams(String requestParams) {
    this.requestParams = requestParams;
  }

  public String getRequestAddress() {
    return requestAddress;
  }

  public void setRequestAddress(String requestAddress) {
    this.requestAddress = requestAddress;
  }

  public Long getExecutedTime() {
    return executedTime;
  }

  public void setExecutedTime(Long executedTime) {
    this.executedTime = executedTime;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
