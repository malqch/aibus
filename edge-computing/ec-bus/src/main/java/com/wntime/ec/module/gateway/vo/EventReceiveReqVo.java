package com.wntime.ec.module.gateway.vo;

import com.wntime.ec.common.util.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wing
 * @create 2020/8/28 16:53
 * @desc
 */
@Data
public class EventReceiveReqVo {
    @NotNull(message = "event_type 不能为空", groups = {AddGroup.class})
    private Integer event_type;

    @NotNull(message = "event_detail 不能为空", groups = {AddGroup.class})
    private Integer event_detail;

    @NotNull(message = "event_time 不能为空", groups = {AddGroup.class})
    private Date event_time;

    @NotEmpty(message = "device_code 不能为空", groups = {AddGroup.class})
    private String device_code;

    Map detail = new HashMap<String, Object>();
}
