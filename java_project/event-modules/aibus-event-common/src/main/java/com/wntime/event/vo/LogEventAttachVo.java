package com.wntime.event.vo;

import com.wntime.event.entity.LogEventAttachEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/9/8 15:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogEventAttachVo extends LogEventAttachEntity {

    private String eventTargetName;
    private String eventTargetCode;
    private String eventTargetGrope;
}
