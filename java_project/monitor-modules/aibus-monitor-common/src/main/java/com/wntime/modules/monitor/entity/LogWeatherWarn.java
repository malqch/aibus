package com.wntime.modules.monitor.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
@Setter
@Getter
@TableName("log_weather_warn")
public class LogWeatherWarn {

  @TableId(value = "weather_warn_id", type = IdType.ID_WORKER)
  private Long weatherWarnId;
  private long areaId;
  private String warnContent;
  private long createdBy;
  private Date createdDate;
  private long modifiedBy;
  private Date modifiedDate;
  private String title;


}
