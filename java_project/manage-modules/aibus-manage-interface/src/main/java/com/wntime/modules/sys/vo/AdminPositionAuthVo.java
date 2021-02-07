package com.wntime.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminPositionAuthVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private long organizeId;
    @JsonSerialize(using = LongToStringSerialize.class)
    private long pid;
    private String label;
    @JsonIgnore
    private int organizeLevel;
    private int selected;
    /**
     * 数据类型，用于区分区域和公司 区域为 0，公司为 1
     */
    private Integer type;

}
