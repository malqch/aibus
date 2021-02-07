package com.wntime.advert.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

/**
 * @author 79448
 * @date 2020/11/6 17:09
 */
@Setter
@Getter
public class OrderAdvertiseDeliveryVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertiseDeliveryId;
    /**
     * @desc 投放方式
     */
    private String advertiseDeliveryType;
    /**
     * @desc 起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryBegin;
    /**
     * @desc 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryEnd;

    /**
     * @desc 是否插播
     * @values 0 不是，1 是
     */
    private Integer isInterrupt;
    /**
     * @desc 插播通知
     */
    private String interruptNotice;
    /**
     * @desc 广告单号
     */
    private String advertiseNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderAdvertiseDeliveryVo that = (OrderAdvertiseDeliveryVo) o;
        return Objects.equals(advertiseDeliveryId, that.advertiseDeliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertiseDeliveryId);
    }
}
