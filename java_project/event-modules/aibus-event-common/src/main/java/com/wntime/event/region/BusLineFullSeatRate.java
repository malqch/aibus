package com.wntime.event.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 公交线路满座率
 * @author 79448
 * @date 2020/8/27 16:05
 */
@Setter
@Getter
@Region("bus_line_full_seat_rate")
public class BusLineFullSeatRate {

    @Id
    private BusLineFullSeatRateKey key;

    /**
     * 公交公司id
     */
    private long companyId;

    private String companyLineName;

    private String companyLineCode;

    /**
     * 满座率
     */
    private double fullSeatRate;

    public void calculateFullSeatRateAvg(double fullSeatRate,int busCount) {
        if(busCount==0)return;
        if (fullSeatRate >= 0) {
            if(busCount==1) {
                this.fullSeatRate=fullSeatRate;
            }else {
                this.fullSeatRate = new BigDecimal(fullSeatRate).add(new BigDecimal(this.fullSeatRate)).divide(new BigDecimal(busCount), 2, RoundingMode.HALF_EVEN).doubleValue();
            }
        }
    }

    public BusLineFullSeatRate() {
    }

    public BusLineFullSeatRate(Long companyLineId) {
        this.key = new BusLineFullSeatRateKey(companyLineId);
    }


}
