package com.wntime.fault.vo;

/**
 * 故障类型数量统计
 */
public class FaultTypeStatVo {

    private Long faultTypeId;
    private String faultTypeName;
    private Integer faultTypeCount;

    public Long getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Long faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public String getFaultTypeName() {
        return faultTypeName;
    }

    public void setFaultTypeName(String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }

    public Integer getFaultTypeCount() {
        return faultTypeCount;
    }

    public void setFaultTypeCount(Integer faultTypeCount) {
        this.faultTypeCount = faultTypeCount;
    }
}
