package com.wntime.event.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * @author 79448
 * @date 2020/8/29 9:56
 */
@Setter
@Getter
@Region("event_type")
public class EventType {
    @Id
    private Long eventTypeId;

    private String eventTypeName;

    private String eventTypeCode;

    private int eventTypeGroup;
}
