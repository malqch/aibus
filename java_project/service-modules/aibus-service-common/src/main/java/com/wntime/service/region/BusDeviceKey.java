package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author 79448
 * @date 2020/9/8 16:56
 */
@Setter
@Getter
public class BusDeviceKey {
    private Long busDeviceId;
    private Long busId;

    public BusDeviceKey() {
    }

    public BusDeviceKey(Long busId,Long busDeviceId) {
        this.busDeviceId = busDeviceId;
        this.busId = busId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusDeviceKey that = (BusDeviceKey) o;
        return busId == that.busId && busDeviceId == that.busDeviceId;
    }

    @Override
    public int hashCode() {
        int hashValue=17;
        hashValue=37*hashValue+ Objects.hashCode(busId);
        hashValue=37*hashValue+ Objects.hashCode(busDeviceId);
        return  hashValue;
    }

    @Override
    public String toString() {
        final StringBuilder builder=new StringBuilder("{ type= ");
        builder.append(getClass().getName());
        builder.append(", busId = ").append(busId);
        builder.append(", busDeviceId = ").append(busDeviceId);
        builder.append(" }");
        return  builder.toString();
    }
}
