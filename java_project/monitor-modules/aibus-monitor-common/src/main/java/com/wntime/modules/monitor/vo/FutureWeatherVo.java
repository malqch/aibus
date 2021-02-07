package com.wntime.modules.monitor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/2 16:29
 */
@Setter
@Getter
@ApiModel
public class FutureWeatherVo {

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "白天天气现象文字")
    private String text_day;

    @ApiModelProperty(value = "白天天气现象代码")
    private String code_day;

    @ApiModelProperty(value = "晚间天气现象文字")
    private String text_night;

    @ApiModelProperty(value = "晚间天气现象代码")
    private String code_night;

    @ApiModelProperty(value = "当天最高温度")
    private String high;

    @ApiModelProperty(value = "当天最低温度")
    private String low;

    @ApiModelProperty(value = "降水概率，范围0~100，单位百分比（目前仅支持国外城市）")
    private String precip;

    @ApiModelProperty(value = "风向文字")
    private String wind_direction;

    @ApiModelProperty(value = "风向角度，范围0~360")
    private String wind_direction_degree;

    @ApiModelProperty(value = "风速，单位km/h（当unit=c时）、mph（当unit=f时）")
    private String wind_speed;

    @ApiModelProperty(value = "风力等级")
    private String wind_scale;

    @ApiModelProperty(value = "降水量，单位mm")
    private String rainfall;

    @ApiModelProperty(value = "相对湿度，0~100，单位为百分比")
    private String humidity;

    private String description;

    public String getDescription() {
        return date+" "+text_day +"最高气温："+high+"°，最低气温："+low+"°";
    }
}
