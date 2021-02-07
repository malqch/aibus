package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminUserRoleVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private long roleId;
    private String roleName;
    private int hasRole;


}
