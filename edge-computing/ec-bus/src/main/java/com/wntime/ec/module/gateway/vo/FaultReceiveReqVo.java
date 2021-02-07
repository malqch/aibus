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
public class FaultReceiveReqVo {
    @NotEmpty(message ="fault_type 不能为空",groups={AddGroup.class})
    private String fault_type;

    @NotEmpty(message ="fault_detail 不能为空",groups={AddGroup.class})
    private String fault_detail;

    @NotNull(message ="fault_time 不能为空",groups={AddGroup.class})
    private Date fault_time;

    private String level;

    Map detail = new HashMap<String, Object>();
}
