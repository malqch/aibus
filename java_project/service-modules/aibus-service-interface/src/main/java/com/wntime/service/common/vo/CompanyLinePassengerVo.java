package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 79448
 * @date 2020/9/15 16:51
 */
@Setter
@Getter
public class CompanyLinePassengerVo {
    private int busGetOn;
    private int busGetOff;
    /**
     * 荷载人数
     */
    private int peopleNumber;
    private String companyLineName;

    public double calculateFullRate(){
        return new BigDecimal(this.busGetOn-busGetOff).divide(new BigDecimal(peopleNumber),2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }



}
