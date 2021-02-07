package com.wntime.modules.monitor.vo;

import com.wntime.service.common.vo.BusDeviceStatusVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/31 10:31
 */
@Setter
@Getter
@ApiModel
public class BusDeviceStatusResponseVo {

    private BusDeviceStatusVo f_o_native_video;
}
