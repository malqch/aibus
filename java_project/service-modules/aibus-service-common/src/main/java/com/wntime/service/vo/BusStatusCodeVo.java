package com.wntime.service.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/9/16 15:00
 */
@Setter
@Getter
public class BusStatusCodeVo {
    private long companyId;
    private String paramCode;
    private long busId;
    private int runStatus;
}
