package com.wntime.ec.module.gateway.vo;

import com.wntime.ec.module.schedule.vo.CarStatusInfo;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wing
 * @create 2020/8/28 16:53
 * @desc
 */
@Data
public class EventUploadReqVo {
    private Long busDeviceId;
    private Long busId;
    private Long collectEventId;
    private Long deviceTypeId;
    private Date eventDate;
    private Long eventLevelId;
    private Long eventTargetId;
    private Long eventTypeId;
    private Map tags = new HashMap();
    private CarStatusInfo carStatusInfo;
}
