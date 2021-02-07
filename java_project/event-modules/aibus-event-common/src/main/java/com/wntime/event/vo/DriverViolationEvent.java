package com.wntime.event.vo;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

import java.util.Objects;

/**
 * @author 79448
 * @date 2020/8/25 18:55
 */

public class DriverViolationEvent implements PdxSerializable {

    private long eventTargetId;

    private String eventTargetName;

    private String eventTargetCode;

    public DriverViolationEvent() {
    }


    public DriverViolationEvent(long eventTargetId, String eventTargetName, String eventTargetCode) {
        this.eventTargetId = eventTargetId;
        this.eventTargetName = eventTargetName;
        this.eventTargetCode = eventTargetCode;
    }

    public long getEventTargetId() {
        return eventTargetId;
    }

    public void setEventTargetId(long eventTargetId) {
        this.eventTargetId = eventTargetId;
    }

    public String getEventTargetName() {
        return eventTargetName;
    }

    public void setEventTargetName(String eventTargetName) {
        this.eventTargetName = eventTargetName;
    }

    public String getEventTargetCode() {
        return eventTargetCode;
    }

    public void setEventTargetCode(String eventTargetCode) {
        this.eventTargetCode = eventTargetCode;
    }

    @Override
    public int hashCode() {
        int hashValue=17;
        hashValue=37 *hashValue+ Objects.hashCode(eventTargetId);
        hashValue=37*hashValue+ Objects.hashCode(eventTargetName);
        hashValue=37*hashValue+ Objects.hashCode(eventTargetCode);
        return hashValue;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if(obj==null || getClass()!=obj.getClass())return false;
        DriverViolationEvent key=(DriverViolationEvent)obj;
        return eventTargetId==key.getEventTargetId() && eventTargetName==key.getEventTargetName() &&eventTargetCode ==key.getEventTargetCode();
    }

    @Override
    public void toData(PdxWriter pdxWriter) {
        pdxWriter.writeLong("eventTargetId",eventTargetId);
        pdxWriter.writeString("eventTargetName",eventTargetName);
        pdxWriter.writeString("eventTargetCode",eventTargetCode);
    }

    @Override
    public void fromData(PdxReader pdxReader) {
        this.eventTargetId=pdxReader.readLong("eventTargetId");
        this.eventTargetName=pdxReader.readString("eventTargetName");
        this.eventTargetCode=pdxReader.readString("eventTargetCode");
    }
}
