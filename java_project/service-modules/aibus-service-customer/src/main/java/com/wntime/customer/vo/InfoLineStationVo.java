package com.wntime.customer.vo;

import com.wntime.customer.entity.InfoLineStationEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/11/5 11:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoLineStationVo extends InfoLineStationEntity {

    private String busStationName;
    private String busStationCode;

    private String companyLineCode;
}
