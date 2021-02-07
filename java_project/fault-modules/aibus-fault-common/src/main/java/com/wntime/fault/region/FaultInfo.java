package com.wntime.fault.region;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author 79448
 * @date 2020/8/27 20:27
 */
@Setter
@Getter
public class FaultInfo {


    private long faultLevelId;

    private String faultLevelName;

    private String faultLevelCode;

    private int count;

    public void incrementCount(){
        this.count+=1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        FaultInfo faultInfo = (FaultInfo) o;
        return faultLevelId == faultInfo.faultLevelId ;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(faultLevelId);
    }
}
