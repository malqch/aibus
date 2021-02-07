package com.wntime.customer.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ysc
 * 2020/11/5 11:03
 */
@Data
public class CompanyLineWithStationVo {

    private Long companyLineId;
    private String companyLineName;
    private String companyLineCode;

    private List<InfoLineStationVo> stationList;
}
