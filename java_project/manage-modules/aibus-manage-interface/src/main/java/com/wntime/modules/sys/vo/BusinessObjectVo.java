package com.wntime.modules.sys.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessObjectVo {

    private long positionId;
    private long areaOrgId;
    private Long busiObjectId;
    private String commName;
    private String icabName;
    private long positionAuthId;
    private int selected;
    private int isSelfonly;

}
