package com.wntime.event.vo;

import com.wntime.event.entity.InfoEventTargetEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/9/7 13:27
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class InfoEventTargetWithUserVo extends InfoEventTargetEntity {
    private String createdUserName;
    private String modifiedUserName;
}
