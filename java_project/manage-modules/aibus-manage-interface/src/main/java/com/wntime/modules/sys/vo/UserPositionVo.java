package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;

public class UserPositionVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private long positionAuthId;
    @JsonSerialize(using = LongToStringSerialize.class)
    private long areaOrgId;
    private String areaName;
    private String systemAuth;
    private int isClique;

    public long getPositionAuthId() {
        return positionAuthId;
    }

    public void setPositionAuthId(long positionAuthId) {
        this.positionAuthId = positionAuthId;
    }

    public long getAreaOrgId() {
        return areaOrgId;
    }

    public void setAreaOrgId(long areaOrgId) {
        this.areaOrgId = areaOrgId;
    }

    public String getSystemAuth() {
        return systemAuth;
    }

    public void setSystemAuth(String systemAuth) {
        this.systemAuth = systemAuth;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getIsClique() {
        return isClique;
    }

    public void setIsClique(int isClique) {
        this.isClique = isClique;
    }
}
