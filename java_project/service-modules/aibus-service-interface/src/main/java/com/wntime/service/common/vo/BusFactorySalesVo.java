package com.wntime.service.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/10/29 17:24
 */
@Setter
@Getter
public class BusFactorySalesVo {

    private String factoryName;

    private double factoryLatitude;

    private double factoryLongitude;

    private String areaName;

    private String companyName;

    private double companyLatitude;

    private double companyLongitude;

    private int orderSum;
}
