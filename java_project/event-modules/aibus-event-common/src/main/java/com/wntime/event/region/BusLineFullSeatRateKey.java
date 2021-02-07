package com.wntime.event.region;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author 79448
 * @date 2020/9/7 9:28
 */
@Setter
@Getter
public class BusLineFullSeatRateKey {

    private Long companyLineId;

    private LocalDate serviceDate;

    public BusLineFullSeatRateKey() {
    }

    public BusLineFullSeatRateKey(Long companyLineId) {
        this.companyLineId = companyLineId;
        this.serviceDate=LocalDate.now();
    }

}
