package com.wntime.fault.vo;

/**
 * 故障类型数量统计
 */
public class FaultCompanyBusStatVo {

    private Long companyId;
    private String companyName;
    private Long busTypeId;
    private String busTypeName;
    private Integer statCount;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getBusTypeId() {
        return busTypeId;
    }

    public void setBusTypeId(Long busTypeId) {
        this.busTypeId = busTypeId;
    }

    public String getBusTypeName() {
        return busTypeName;
    }

    public void setBusTypeName(String busTypeName) {
        this.busTypeName = busTypeName;
    }

    public Integer getStatCount() {
        return statCount;
    }

    public void setStatCount(Integer statCount) {
        this.statCount = statCount;
    }
}
