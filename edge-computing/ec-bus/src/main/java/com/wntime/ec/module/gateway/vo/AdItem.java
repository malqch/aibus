package com.wntime.ec.module.gateway.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2020/11/10 10:10
 * @desc
 */
@Data
public class AdItem implements Comparable<AdItem> {
    private Long adDeliveryId;
    private int score;

    @Override
    public int compareTo(AdItem a) {
        return a.getScore() - this.score;
    }
}
