package com.wntime.event.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * 乘客总数概览
 * @author 79448
 * @date 2020/9/14 14:37
 */
@Setter
@Getter
@Region("passenger_summary")
public class PassengerSummary {
    @Id
    private Long factoryId;
    private int maleCount;
    private int femaleCount;
    private int childCount;
    private int adultCount;
    private int oldCount;

    private int passengerCount;

    public PassengerSummary() {
    }

    public PassengerSummary(Long factoryId) {
        this.factoryId = factoryId;
    }

    public void incrementMale(){
        this.maleCount+=1;
    }
    public void incrementFemale(){
        this.femaleCount+=1;
    }
    public void incrementChild(){
        this.childCount+=1;
    }

    public void incrementAdult(){
        this.adultCount+=1;
    }

    public void incrementOld(){
        this.oldCount+=1;
    }
    public void incrementPassenger(){
        this.passengerCount+=1;
    }
}
