package com.wntime.advert.vo;

import com.wntime.service.entity.InfoConfigParamEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ysc
 * 2020/11/5 15:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVo {

    private Long itemId;
    private String itemName;
    private String itemCode;
    private String itemGroup;

    public ItemVo(InfoConfigParamEntity infoConfigParamEntity){
        this.itemId = infoConfigParamEntity.getConfigParamId();
        this.itemName = infoConfigParamEntity.getParamName();
        this.itemCode = infoConfigParamEntity.getParamCode();
        this.itemGroup = infoConfigParamEntity.getParamGroup();
    }
}
