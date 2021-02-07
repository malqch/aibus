package com.wntime.event.vo;

import com.wntime.event.entity.InfoEventExtendEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/9/7 15:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoEventExtendDetailVo extends InfoEventExtendEntity {

    private String eventTargetName;
    private String eventTargetCode;
    private String eventTargetGrope;

    private String createdUserName;
    private String modifiedUserName;
}
