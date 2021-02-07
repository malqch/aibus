package com.wntime.ec.module.schedule.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/9/1 15:31
 * @desc
 */
@Data
public class DsmAlarmRspVo {
    private String alarmtype;
    private String driverName;
    private String position;
    private String attachfiledir;
    private Long uploadtime;
}