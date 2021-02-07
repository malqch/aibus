package com.wntime.advert.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ysc
 * 2020/11/7 14:17
 */
@Data
public class AuditVo {

    private Long advertiseId;
    // 0为未通过，1为通过
    private int auditResult;
    private List<Long> checkItemList;
    private String checkSuggest;
}
