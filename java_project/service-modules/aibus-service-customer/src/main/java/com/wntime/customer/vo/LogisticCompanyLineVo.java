package com.wntime.customer.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ysc
 * 2020/11/9 17:07
 */
@Data
public class LogisticCompanyLineVo {

    private String companyLineCode;
    private List<List<InfoLineStationVo>> list;
}
