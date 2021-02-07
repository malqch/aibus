package com.wntime.fault.form;

import com.wntime.service.common.form.CarStatusInfoForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

/**
 * @author 79448
 * @date 2020/8/26 14:00
 */
@ApiModel
@Setter
@Getter
public class FaultReportForm {

    @Positive(message = "故障采集id必须大于零")
    @NotNull(message = "故障采集id不能为空")
    @ApiModelProperty(value = "故障采集id",required = true,dataType = "java.lang.Long")
    private Long collectEventId;

    @Positive(message = "公交车id必须大于零")
    @NotNull(message = "公交车id不能为空")
    @ApiModelProperty(value = "公交车id",required = true,dataType = "java.lang.Long")
    private Long busId;

    @Positive(message = "故障类型id必须大于零")
    @NotNull(message = "故障类型id不能为空")
    @ApiModelProperty(value = "故障类型id",required = true,dataType = "java.lang.Long")
    private Long eventTypeId;

    @Positive(message = "故障标签id必须大于零")
    @NotNull(message = "故障标签id不能为空")
    @ApiModelProperty(value = "故障标签id",required = true,dataType = "java.lang.Long")
    private Long eventTargetId;

    @Positive(message = "故障等级id必须大于零")
    @NotNull(message = "故障等级id不能为空")
    @ApiModelProperty(value = "故障等级id",required = true,dataType = "java.lang.Long")
    private Long eventLevelId;

    @NotNull(message = "标签参数不能为空")
    @ApiModelProperty(value = "标签参数",required = true,dataType = "java.util.Map")
    private Map<String,Object> tags;

    @NotNull(message = "车辆行驶数据不能为空")
    @ApiModelProperty(value = "车辆状态数据" ,required = true)
    private CarStatusInfoForm carStatusInfo;

    @NotNull(message = "故障时间不能为空")
    @ApiModelProperty(value = "故障时间" ,required = true)
    private Long eventDate;


    @ApiModelProperty(hidden = true)
    private String faultTypeName;

    @ApiModelProperty(hidden = true)
    private String faultLevelName;
    @ApiModelProperty(hidden = true)
    private String faultLevelCode;
    @ApiModelProperty(hidden = true)
    private String faultTargetCode;
}
