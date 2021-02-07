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
public class OrderDeliveryAreaVo {
    private Long deliveryAreaId;
    /**
     * @desc 广告投放Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertiseDeliveryId;
    /**
     * @desc 公交线路Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long companyLineId;
    /**
     * @desc 线路车站Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long lineStationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDeliveryAreaVo areaVo = (OrderDeliveryAreaVo) o;
        return Objects.equals(deliveryAreaId, areaVo.deliveryAreaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryAreaId);
    }
}
