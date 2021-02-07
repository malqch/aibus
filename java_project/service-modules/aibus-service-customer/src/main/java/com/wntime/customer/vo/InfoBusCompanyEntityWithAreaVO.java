package com.wntime.customer.vo;

import com.wntime.customer.entity.InfoBusCompanyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/8/25 15:34
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class InfoBusCompanyEntityWithAreaVO extends InfoBusCompanyEntity {

    private String areaName;
}
