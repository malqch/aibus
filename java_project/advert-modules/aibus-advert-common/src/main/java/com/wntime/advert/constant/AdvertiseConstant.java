package com.wntime.advert.constant;

/**
 * @author ysc
 * 2020/11/11 9:01
 */
public class AdvertiseConstant {

    //是否插播
    public static final int NOT_INTERRUPT = 0;
    public static final int IS_INTERRUPT = 1;

    //AdvertiseDeliveryType
    public static final String STATION = "station";
    public static final String LINE = "line";

    //CheckStatus
    public static final int DRAFT = 0;
    public static final int AUDIT = 1;
    public static final int UNCONFIRMED = 2;
    public static final int DELIVERY = 3;
    public static final int FAIL = 4;
    public static final int OFFLINE = 9;
}
