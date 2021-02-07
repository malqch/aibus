package com.wntime.advert.vo;

import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.wntime.advert.entity.OrderDeliveryAreaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * 给前端返回信息用
 * @author ysc
 * 2020/11/10 16:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdvertiseInfoVo extends OrderAdvertiseDeliveryEntity {

    private List<String> outDeliveryLineList;
    private List<OrderDeliveryAreaEntity> deliveryStationList;
    private List<Long> peopleAgeList;
    private List<Long> peopleGenderList;

    private Map<String,AdvertiseAttachVo> attachList;
    private List<String> positionDescList;

    private String advertiseDeliveryTypeName;
    private String advertiseDeliveryTypeCode;
}
