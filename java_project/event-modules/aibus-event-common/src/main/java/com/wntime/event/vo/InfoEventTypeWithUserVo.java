package com.wntime.event.vo;

import com.wntime.event.entity.InfoEventTypeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/9/7 13:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoEventTypeWithUserVo extends InfoEventTypeEntity {

    private String createdUserName;
    private String modifiedUserName;
}
