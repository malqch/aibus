package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AdminPositionVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long positionId;
    private String name;
    private String description;

    private String systemAuth="manage";
    private int isEnabled;
    private int isDeleted;
    private String modifyUserName;
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long modifyUserId;
    private Timestamp modifyDt;
    private String createUserName;
    private Timestamp createDt;
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long createUserId;
    private List<AdminPositionAuthVo> positionAuth;


}
