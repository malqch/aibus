package com.wntime.ec.module.gateway.vo;

import com.wntime.ec.common.util.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author wing
 * @create 2020/8/28 16:53
 * @desc
 */
@Data
public class StatusReceiveReqVo {
    @NotEmpty(message ="device_code 不能为空",groups={AddGroup.class})
    private String device_code;

    @NotNull(message ="device_status 不能为空",groups={AddGroup.class})
    private Integer device_status;

    @NotNull(message ="event_time 不能为空",groups={AddGroup.class})
    private Date event_time;
}
