package com.wntime.event.vo;

import com.wntime.event.entity.InfoCollectEventEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ysc
 * 2020/9/7 15:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoCollectEventDetailVo extends InfoCollectEventEntity {

    private String eventTypeName;
    private String eventTargetName;
    private String eventLevelName;
    private String deviceTypeName;
    /**
     * 事件大类小类 只用于和外部交互
     */
    //private String eventTypeName;
    //private String eventDetailName;

    /**
     * 事件描述相关
     */
    private Long eventDescriptionId;
    private String descriptionContent;

    private String createdUserName;
    private String modifiedUserName;

    private List<InfoEventExtendDetailVo> eventExtendList;
}
