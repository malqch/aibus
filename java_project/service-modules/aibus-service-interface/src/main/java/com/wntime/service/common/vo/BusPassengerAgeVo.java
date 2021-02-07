package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/6 15:16
 */
@Setter
@Getter
public class BusPassengerAgeVo {
    private int childrenTotal;
    private int adultTotal;
    private int oldTotal;

    private int maleTotal;
    private int femaleTotal;

    public int getMax() {
        int max = 0;
        max = childrenTotal > adultTotal ? childrenTotal : adultTotal;
        max = max > oldTotal ? max : oldTotal;
        return max;
    }
}
