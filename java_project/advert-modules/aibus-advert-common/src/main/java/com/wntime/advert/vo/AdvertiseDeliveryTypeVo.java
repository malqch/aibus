package com.wntime.advert.vo;

import com.wntime.service.entity.InfoConfigParamEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ysc
 * 2020/11/6 9:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertiseDeliveryTypeVo {

    private Long advertiseDeliveryType;
    private String advertiseDeliveryTypeName;
    private String advertiseDeliveryTypeCode;

    public AdvertiseDeliveryTypeVo(InfoConfigParamEntity infoConfigParamEntity){
        this.advertiseDeliveryType = infoConfigParamEntity.getConfigParamId();
        this.advertiseDeliveryTypeName = infoConfigParamEntity.getParamName();
        this.advertiseDeliveryTypeCode = infoConfigParamEntity.getParamCode();
    }
}
