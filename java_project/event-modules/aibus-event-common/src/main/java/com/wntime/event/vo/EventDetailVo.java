package com.wntime.event.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 79448
 * @date 2020/11/3 14:18
 */
@Setter
@Getter
public class EventDetailVo {

    private long eventDetailId;
    private long eventTypeId;
    private  String eventTypeCode;
    private String eventTypeName ;
    private String eventLevelName ;
    private String descriptionContent ;
    private Date createdDate ;
    private Double collectAttachValue;
    private String collectAttachChar;
    private Long collectAttachLink;
    private String eventTargetGrope;
    private String eventTargetCode;
}
