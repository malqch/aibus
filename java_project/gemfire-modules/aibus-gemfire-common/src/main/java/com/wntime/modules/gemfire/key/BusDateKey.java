package com.wntime.modules.gemfire.key;


import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author 79448
 * @date 2020/9/7 9:33
 */

public class BusDateKey implements PdxSerializable {

    private Long busId;

    private LocalDate serviceDate;

    public BusDateKey() {
    }

    public BusDateKey(Long busId) {
        this.busId = busId;
        this.serviceDate=LocalDate.now();
    }

    public BusDateKey(Long busId, LocalDate date) {
        this.busId = busId;
        this.serviceDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusDateKey that = (BusDateKey) o;
        return busId == that.busId && serviceDate.equals(that.serviceDate);
    }

    @Override
    public int hashCode() {
        int hashValue=17;
        hashValue=37*hashValue+ Objects.hashCode(busId);
        hashValue=37*hashValue+ Objects.hashCode(serviceDate);
        return  hashValue;
    }

    @Override
    public String toString() {
        final StringBuilder builder=new StringBuilder("{ type= ");
        builder.append(getClass().getName());
        builder.append(", busId = ").append(busId);
        builder.append(", serviceDate = ").append(serviceDate.toString());
        builder.append(" }");
        return  builder.toString();
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Override
    public void toData(PdxWriter writer) {
        writer.writeLong("busId",busId);
        writer.writeObject("serviceDate",serviceDate);
    }

    @Override
    public void fromData(PdxReader reader) {
        this.busId=reader.readLong("busId");
        this.serviceDate= (LocalDate) reader.readObject("serviceDate");
    }
}
