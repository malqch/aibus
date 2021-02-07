package com.wntime.modules.officer.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;


public class LineStationVO implements Comparable<LineStationVO> {

    private Long companyLineId;
    private Long busStationId;
    private String busStationName;
    private Integer stationOrder;

    public Long getCompanyLineId() {
        return companyLineId;
    }

    public void setCompanyLineId(Long companyLineId) {
        this.companyLineId = companyLineId;
    }

    public Long getBusStationId() {
        return busStationId;
    }

    public void setBusStationId(Long busStationId) {
        this.busStationId = busStationId;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName;
    }

    public Integer getStationOrder() {
        return stationOrder;
    }

    public void setStationOrder(Integer stationOrder) {
        this.stationOrder = stationOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineStationVO)) return false;
        LineStationVO that = (LineStationVO) o;
        return Objects.equals(getCompanyLineId(), that.getCompanyLineId()) &&
                Objects.equals(getBusStationId(), that.getBusStationId()) &&
                Objects.equals(getBusStationName(), that.getBusStationName()) &&
                Objects.equals(getStationOrder(), that.getStationOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyLineId(), getBusStationId(), getBusStationName(), getStationOrder());
    }

    @Override
    public int compareTo(LineStationVO o) {
        return stationOrder.compareTo(o.stationOrder);
    }
}
