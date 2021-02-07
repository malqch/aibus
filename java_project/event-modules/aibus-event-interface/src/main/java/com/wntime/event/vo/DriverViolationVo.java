package com.wntime.event.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/27 14:37
 */
@Setter
@Getter
public class DriverViolationVo {

    private int count;

    private String name;

    public DriverViolationVo( String name,int count) {
        this.count = count;
        this.name = name;
    }

    public void incrementCount(){
        this.count+=1;
    }
}
