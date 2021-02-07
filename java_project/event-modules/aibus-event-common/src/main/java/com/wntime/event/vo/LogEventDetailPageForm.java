package com.wntime.event.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ysc
 * 2020/9/8 18:48
 */
@Data
public class LogEventDetailPageForm {

    private Integer page;
    private Integer limit;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String vinCode;

    private Long collectEventId;
    private Long eventLevelId;
    private Long eventTypeId;
    private Long eventTargetId;

    private Long busDeviceId;
    private Long deviceTypeId;
}
