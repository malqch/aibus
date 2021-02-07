package com.wntime.advert.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 79448
 * @date 2020/11/6 17:08
 */
@Setter
@Getter
public class OrderAdvertiseAttachVo {
    private Long advertiseAttachId;
    /**
     * @desc 广告投放Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertiseDeliveryId;
    /**
     * @desc 广告位Id
     */
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertisePositionId;
    /**
     * @desc 播放时长
     */
    private Double showTimes;
    /**
     * @desc 素材类型  0 1
     */
    private int attachType;
    /**
     * @desc 素材地址
     */
    private String attachLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        OrderAdvertiseAttachVo that = (OrderAdvertiseAttachVo) o;
        return Objects.equals(advertiseAttachId, that.advertiseAttachId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertiseAttachId);
    }
}
