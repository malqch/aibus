package com.wntime.maintain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ysc
 * 2020/8/26 16:58
 */

@Data
public class MaintainBusVO {

    private Long busId;
    private String vinCode;
    private Date createdDate;
    private Long busStatusId;
    private String busStatusCode;
    private String busStatusName;
}
