package com.wntime.event.vo;

import com.wntime.event.entity.LogEventDetailEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ysc
 * 2020/9/8 15:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogEventDetailVo extends LogEventDetailEntity {

    private String collectEvent;
    private String eventTypeName;
    private String eventTargetName;
    private String eventLevelName;

    private String busDeviceName;
    private String deviceTypeName;

    private String vinCode;
    private String plateCode;
    private String busCode;

    private List<LogEventAttachVo> eventAttachList;
}
