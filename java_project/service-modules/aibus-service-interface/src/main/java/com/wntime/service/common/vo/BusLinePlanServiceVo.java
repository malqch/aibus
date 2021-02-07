package com.wntime.service.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 79448
 * @date 2020/11/2 17:27
 */
@Setter
@Getter
public class BusLinePlanServiceVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long planServiceId;
    /**
     * @desc 公交车Id
     */
    private Long busId;

    /**
     * @desc 公交线路Id
     */
    private Long companyLineId;
    /**
     * @desc 起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    /**
     * @desc 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
