package com.wntime.modules.monitor.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/26 11:38
 */
@Setter
@Getter
public class OperationStatisticsCountVo {
    private String title;
    @JsonSerialize(using = LongToStringSerialize.class)
    private long count;

    public OperationStatisticsCountVo(String title, long count) {
        this.title = title;
        this.count = count;
    }
}
