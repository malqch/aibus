package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;

import java.io.Serializable;

public class UserRoleVo implements Serializable {
    private static final long serialVersionUID = -7968680103921524408L;
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long roleId;
    private String roleName;

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long areaId;
    private String areaName;



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
