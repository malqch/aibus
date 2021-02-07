package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 79448
 * @date 2020/8/31 18:01
 */
@Setter
@Getter
@Region("bus_status_summary")
public class BusStatusSummary {

    @Id
    private Long companyId;

    private int busCount;

    private int normalCount;

    private int faultCount;

    private int waitMaintainCount;

    private int maintainCount;


    public BusStatusSummary() {
    }

    public BusStatusSummary(Long companyId) {
        this.companyId = companyId;
    }

    public void incrementBusCount() {
        this.busCount += 1;
    }

    public void decrementBusCount() {
        if (this.busCount > 0) {
            this.busCount -= 1;
        }
    }

    public void incrementNormalCount() {
        if (this.normalCount < this.busCount) {
            this.normalCount += 1;
        }
    }

    public void decrementNormalCount() {
        if (this.normalCount > 0) {
            this.normalCount -= 1;
        }
    }

    public void incrementFaultCount() {
        if (this.faultCount < this.busCount) {
            this.faultCount += 1;
        }
    }

    public void decrementFaultCount() {
        if (this.faultCount > 0) {
            this.faultCount -= 1;
        }
    }

    public void incrementWaitMaintainCount() {
        if (this.waitMaintainCount < this.busCount) {
            this.waitMaintainCount += 1;
        }
    }

    public void decrementWaitMaintainCount() {
        if (this.waitMaintainCount > 0) {
            this.waitMaintainCount -= 1;
        }
    }

    public void incrementMaintainCount() {
        if (this.maintainCount < this.busCount) {
            this.maintainCount += 1;
        }
    }

    public void decrementMaintainCount() {
        if (this.maintainCount > 0) {
            this.maintainCount -= 1;
        }
    }
}
