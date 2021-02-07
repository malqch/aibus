package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusiObjectVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private long busiObjectId;
    private String name;

}
