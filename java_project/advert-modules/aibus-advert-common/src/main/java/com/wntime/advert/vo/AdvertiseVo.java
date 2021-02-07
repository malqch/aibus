package com.wntime.advert.vo;

import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.wntime.advert.entity.OrderDeliveryAreaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author ysc
 * 2020/11/6 9:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdvertiseVo extends OrderAdvertiseDeliveryEntity {

    private List<String> deliveryLineList;

    private List<OrderDeliveryAreaEntity> deliveryStationList;
    private List<Long> peopleAgeList;
    private List<Long> peopleGenderList;

    private List<AdvertiseAttachVo> attachList;
    private List<String> positionDescList;

    private String advertiseDeliveryTypeName;
    private String advertiseDeliveryTypeCode;
}
