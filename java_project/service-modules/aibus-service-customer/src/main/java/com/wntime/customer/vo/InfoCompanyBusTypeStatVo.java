package com.wntime.customer.vo;

/**
 * 分车型订单总量、交付量
 */
public class InfoCompanyBusTypeStatVo {
    private Long id;
    private Long busTypeId;
    private String firstName;
    private Integer firstCount;
    private String secondName;
    private Integer secondCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusTypeId() {
        return busTypeId;
    }

    public void setBusTypeId(Long busTypeId) {
        this.busTypeId = busTypeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(Integer firstCount) {
        this.firstCount = firstCount;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(Integer secondCount) {
        this.secondCount = secondCount;
    }
}
