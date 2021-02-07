package com.wntime.event.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 79448
 * @date 2020/8/29 9:41
 */
@Setter
@Getter
@ApiModel
public class BusAlarmInfoVo {
    @ApiModelProperty("告警类型id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long id;
    @ApiModelProperty("告警类型名称")
    private String name;
    @ApiModelProperty("告警列表")
    private List<AlarmInfoItem> info;

    public BusAlarmInfoVo() {
        this.info=new ArrayList<>();
    }

    public void addAlarm(AlarmInfoItem infoItem){
        this.info.add(infoItem);
    }

    public BusAlarmInfoVo(long id, String name) {
        this.id = id;
        this.name = name;
        this.info=new ArrayList<>();
    }
}
