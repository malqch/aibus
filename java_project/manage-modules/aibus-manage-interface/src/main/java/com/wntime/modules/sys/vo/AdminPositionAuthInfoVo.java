package com.wntime.modules.sys.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminPositionAuthInfoVo {
    private long positionAuthId;
    private long positionId;
    private long areaOrgId;
    private long isSelfonly;
    private int linkType;

}
