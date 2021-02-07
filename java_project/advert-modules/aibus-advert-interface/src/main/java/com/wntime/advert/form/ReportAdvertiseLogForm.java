package com.wntime.advert.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author 79448
 * @date 2020/11/10 14:32
 */
@Setter
@Getter
@ApiModel
public class ReportAdvertiseLogForm {

    @ApiModelProperty(value = "广告投放单id")
    @NotNull(message = "广告投放单id不能为空")
    private Long advertiseDeliveryId;

    @ApiModelProperty(value = "公交车id")
    @NotNull(message = "公交车id不能为空")
    private Long busId;

    @ApiModelProperty(value = "公交线路id")
    @NotNull(message = "公交线路id不能为空")
    private Long companyLineId;

    /**
     * @desc 广告位Id
     */
    @ApiModelProperty(value = "广告位Id")
    @NotNull(message = "广告位Id不能为空")
    private Long advertisePositionId;

    /**
     * @desc 播放时长
     */
    @Positive(message = "广告播放时长不能小于零")
    @NotNull(message = "广告播放时长不能为空")
    private Double showTimes;
    /**
     * @desc 素材类型
     */
    @Range(min = 0,max = 2,message = "素材类型不正确")
    @NotNull(message = "素材类型不能为空")
    private Long attachType;


    private int peopleCount;
}
