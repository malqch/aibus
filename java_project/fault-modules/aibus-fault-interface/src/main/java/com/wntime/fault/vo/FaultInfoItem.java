package com.wntime.fault.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 79448
 * @date 2020/9/1 9:31
 */
@Setter
@Getter
public class FaultInfoItem implements Comparable<FaultInfoItem>{

    private String type;

    private String desc;

    private String level;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date faultDate;

    @Override
    public int compareTo(FaultInfoItem o) {
        int compare=o.getFaultDate().compareTo(this.faultDate);
        if(compare==0){
            return 1;
        }else
        return compare;
    }
}
