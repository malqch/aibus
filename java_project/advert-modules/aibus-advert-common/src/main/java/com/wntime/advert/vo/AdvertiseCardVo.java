package com.wntime.advert.vo;

import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ysc
 * 2020/11/5 15:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdvertiseCardVo extends OrderAdvertiseDeliveryEntity {

    private String deviceName;

    private String checkStatusName;
    private String checkStatusCode;

    private List<ItemVo> checkItemList = new ArrayList<>();
    private String checkSuggest;

    private int totalTime;
    private int useTime;
    private String remainingTime;
}
