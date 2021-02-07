package com.wntime.event.form;

import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.service.common.form.CarStatusInfoForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

@Getter
@Setter
@ApiModel
public class EventReportForm {

    @Positive(message = "事件采集id必须大于零")
    @NotNull(message = "事件采集id不能为空")
    @ApiModelProperty(value = "事件采集id",required = true,dataType = "java.lang.Long")
    private Long collectEventId;

    @Positive(message = "公交车id必须大于零")
    @NotNull(message = "公交车id不能为空")
    @ApiModelProperty(value = "公交车id",required = true,dataType = "java.lang.Long")
    private Long busId;

    @Positive(message = "设备id必须大于零")
    @NotNull(message = "设备id不能为空")
    @ApiModelProperty(value = "设备id",required = true,dataType = "java.lang.Long")
    private Long busDeviceId;

    @Positive(message = "设备类型id必须大于零")
    @NotNull(message = "设备类型id不能为空")
    @ApiModelProperty(value = "设备类型id",required = true,dataType = "java.lang.Long")
    private Long deviceTypeId;

    @Positive(message = "事件类型id必须大于零")
    @NotNull(message = "事件类型id不能为空")
    @ApiModelProperty(value = "事件类型id",required = true,dataType = "java.lang.Long")
    private Long eventTypeId;

    @Positive(message = "事件标签id必须大于零")
    @NotNull(message = "事件标签id不能为空")
    @ApiModelProperty(value = "事件标签id",required = true,dataType = "java.lang.Long")
    private Long eventTargetId;

    @Positive(message = "事件等级id必须大于零")
    @NotNull(message = "事件等级id不能为空")
    @ApiModelProperty(value = "事件等级id",required = true,dataType = "java.lang.Long")
    private Long eventLevelId;

    @NotNull(message = "标签参数不能为空")
    @ApiModelProperty(value = "标签参数",required = true,dataType = "java.util.Map")
    private Map<String,Object> tags;

//    @NotNull(message = "车辆行驶数据不能为空")
    @ApiModelProperty(value = "车辆状态数据" ,required = true)
    private CarStatusInfoForm carStatusInfo;

    @NotNull(message = "事件时间不能为空")
    @ApiModelProperty(value = "事件时间" ,required = true)
    private Long eventDate;

    @ApiModelProperty(hidden=true)
    private String eventTargetName;
    @ApiModelProperty(hidden=true)
    private String eventTargetCode;
    @ApiModelProperty(hidden=true)
    private Long eventDetailId;

    @ApiModelProperty(hidden=true)
    private String eventTypeName;
    @ApiModelProperty(hidden=true)
    private String eventTypeCode;
    @ApiModelProperty(hidden=true)
    private String eventLevelName;
    /**
     * 事件小类
     */
    @ApiModelProperty(hidden=true)
    private int eventDetail;

    /**
     * 行程
     */
    @ApiModelProperty(hidden=true)
    private ItineraryReceiptEntity itineraryReceipt;
}
