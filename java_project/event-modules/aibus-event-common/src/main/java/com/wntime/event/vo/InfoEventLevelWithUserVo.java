package com.wntime.event.vo;

import com.wntime.event.entity.InfoEventLevelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/9/7 13:19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class InfoEventLevelWithUserVo extends InfoEventLevelEntity {

    private String createdUserName;
    private String modifiedUserName;

}
