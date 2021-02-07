package com.wntime.dto;

import java.io.Serializable;

public class RoleOther implements Serializable {

    private static final long serialVersionUID = 5085299025814363834L;

    private String roleId;

    private String name;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
