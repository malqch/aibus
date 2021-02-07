package com.wntime.fault.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author 79448
 * @date 2020/9/1 8:54
 */
@Setter
@Getter
@ApiModel
public class BusFaultInfoVo {

    @ApiModelProperty("告警类型id")
    @JsonSerialize(using = LongToStringSerialize.class)
    private long id;
    @ApiModelProperty("告警类型名称")
    private String name;
    @ApiModelProperty("告警列表")
    private Set<FaultInfoItem> info;

    public BusFaultInfoVo() {
        this.info=new TreeSet<>();
    }

    public void addFault(FaultInfoItem infoItem){
        this.info.add(infoItem);
    }

}
