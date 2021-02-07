package com.wntime.advert.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author 79448
 * @date 2020/11/6 17:09
 */
@Setter
@Getter
public class OrderDeliveryTargetVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long deliveryTargetId;
    /**
     * @desc 广告投放Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertiseDeliveryId;
    /**
     * @desc 广告标签Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertiseTargetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDeliveryTargetVo targetVo = (OrderDeliveryTargetVo) o;
        return Objects.equals(deliveryTargetId, targetVo.deliveryTargetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryTargetId);
    }
}
