package com.wntime.modules.gemfire.vo;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

/**
 * @author 79448
 * @date 2020/8/28 10:10
 */
public class LineStationInfoVo implements PdxSerializable {

    /**
     * 公交线路Id
     */
    private long companyLineId;
    private String companyLineCode;
    private String companyLineName;
    private long companyId;
    /**
     * 公交车站Id
     */
    private long busStationId;
    /**
     * 下一站id 最后一站时为空
     */
    private Long nextStationId;

    /**
     * 车站序号
     */
    private int stationOrder;


    private String busStationName;
    private double busStationLongitude;
    private double busStationLatitude;
    private int busStationDeviation;


    @Override
    public void toData(PdxWriter writer) {
        writer.writeLong("companyLineId", companyLineId);
        writer.writeLong("busStationId", busStationId);
        writer.writeObject("nextStationId", nextStationId);
        writer.writeInt("stationOrder", stationOrder);
        writer.writeString("busStationName", busStationName);
        writer.writeDouble("busStationLongitude", busStationLongitude);
        writer.writeDouble("busStationLatitude", busStationLatitude);
        writer.writeInt("busStationDeviation", busStationDeviation);
        writer.writeString("companyLineCode",companyLineCode);
        writer.writeString("companyLineName",companyLineName);
        writer.writeLong("companyId",companyId);
    }

    @Override
    public void fromData(PdxReader reader) {
        this.companyLineId = reader.readLong("companyLineId");
        this.busStationId = reader.readLong("busStationId");
        this.nextStationId = (Long)reader.readObject("nextStationId");
        this.stationOrder = reader.readInt("stationOrder");
        this.busStationName = reader.readString("busStationName");
        this.busStationLongitude = reader.readDouble("busStationLongitude");
        this.busStationLatitude = reader.readDouble("busStationLatitude");
        this.busStationDeviation = reader.readInt("busStationDeviation");
        this.companyLineName=reader.readString("companyLineName");
        this.companyLineCode=reader.readString("companyLineCode");
        this.companyId = reader.readLong("companyId");
    }

    public long getCompanyLineId() {
        return companyLineId;
    }

    public void setCompanyLineId(long companyLineId) {
        this.companyLineId = companyLineId;
    }

    public long getBusStationId() {
        return busStationId;
    }

    public void setBusStationId(long busStationId) {
        this.busStationId = busStationId;
    }

    public Long getNextStationId() {
        return nextStationId;
    }

    public void setNextStationId(Long nextStationId) {
        this.nextStationId = nextStationId;
    }

    public int getStationOrder() {
        return stationOrder;
    }

    public void setStationOrder(int stationOrder) {
        this.stationOrder = stationOrder;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName;
    }

    public double getBusStationLongitude() {
        return busStationLongitude;
    }

    public void setBusStationLongitude(double busStationLongitude) {
        this.busStationLongitude = busStationLongitude;
    }

    public double getBusStationLatitude() {
        return busStationLatitude;
    }

    public void setBusStationLatitude(double busStationLatitude) {
        this.busStationLatitude = busStationLatitude;
    }

    public int getBusStationDeviation() {
        return busStationDeviation;
    }

    public void setBusStationDeviation(int busStationDeviation) {
        this.busStationDeviation = busStationDeviation;
    }

    public String getCompanyLineCode() {
        return companyLineCode;
    }

    public void setCompanyLineCode(String companyLineCode) {
        this.companyLineCode = companyLineCode;
    }

    public String getCompanyLineName() {
        return companyLineName;
    }

    public void setCompanyLineName(String companyLineName) {
        this.companyLineName = companyLineName;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
