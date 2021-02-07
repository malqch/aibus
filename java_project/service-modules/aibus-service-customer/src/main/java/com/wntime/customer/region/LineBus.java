package com.wntime.customer.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Date;

/**
 * @author 79448
 * @date 2020/8/27 18:32
 */
@Setter
@Getter
@Region("line_bus")
public class LineBus {

    @Id
    private Long busId;

    private Long areaId;

    private String areaName;

    private Long companyId;

    private String companyName;

    private String companyLineName;

    private String companyLineCode;

    private Long companyLineId;

    private Date firstTime;
    private Date lastTime;
}
