package com.wntime.customer.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ysc
 * 2020/11/7 20:26
 */
@Data
public class AdvertlineVo {

    private List<Long> lineIdList;
    private String companyLineCode;
}
