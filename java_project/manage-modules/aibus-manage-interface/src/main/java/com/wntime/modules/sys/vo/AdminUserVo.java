package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AdminUserVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long userId;
    private String userName;

    private String loginName;

    private String email;
    private String mobile;
    private int isEnabled;
    private int isDeleted;

    private List<AdminUserRoleVo> roles;
    private Collection<AdminUserPositionVo> positions;

}
