package com.wntime.advert.vo;

import com.wntime.advert.entity.OrderDeliveryTargetEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/11/7 13:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDeliveryTargetVo extends OrderDeliveryTargetEntity {

    private String advertiseTargetName;
    private String advertiseTargetCode;
    private String advertiseTargetGrope;
}
