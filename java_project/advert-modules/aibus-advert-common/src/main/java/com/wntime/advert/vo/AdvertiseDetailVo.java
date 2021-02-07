package com.wntime.advert.vo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ysc
 * 2020/11/10 18:02
 */
@Data
public class AdvertiseDetailVo {

    private Long advertiseId;
    private int isInterrupt;
    private String interruptNotice;
    private int checkStatus;
    private String advertiseDeliveryTypeName;
    private String deliveryDate;
    private String deliveryLine;
    private String deliveryStation;
    private String deliveryPeopleAge;
    private String deliveryPeopleGender;
    private Map<String,AdvertiseAttachVo> attachList = new LinkedHashMap<>();
    private String positionDesc;
}
