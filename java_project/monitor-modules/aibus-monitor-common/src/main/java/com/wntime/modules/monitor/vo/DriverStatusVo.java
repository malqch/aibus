package com.wntime.modules.monitor.vo;

import com.wntime.service.common.vo.MonitorItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/8/31 10:25
 */
@Setter
@Getter
@ApiModel
public class DriverStatusVo {
    @ApiModelProperty("疲劳驾驶违规")
    private MonitorItemVo fatigue;
    @ApiModelProperty("喝水违规")
    private MonitorItemVo drink;
    @ApiModelProperty("打手机违规")
    private MonitorItemVo playPhone ;
    @ApiModelProperty("吸烟违规")
    private MonitorItemVo smoke;
}
