package com.wntime.modules.monitor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 79448
 * @date 2020/10/30 10:58
 */
@Setter
@Getter
@ApiModel
public class WeatherVo {

    @ApiModelProperty(value = "天气现象文字")
    private String text;
    @ApiModelProperty(value = "天气现象代码")
    private String code;
    @ApiModelProperty(value = "温度 单位为c摄氏度或f华氏度")
    private String temperature;
    @ApiModelProperty(value = "体感温度 单位为c摄氏度或f华氏度")
    private String feels_like;
    @ApiModelProperty(value = "气压 单位为mb百帕或in英寸")
    private String pressure;
    @ApiModelProperty(value = "相对湿度，0~100，单位为百分比")
    private String humidity;
    @ApiModelProperty(value = "能见度，单位为km公里或mi英里")
    private String visibility;
    @ApiModelProperty(value = "风向文字")
    private String wind_direction;
    @ApiModelProperty(value = "风向角度，范围0~360，0为正北，90为正东，180为正南，270为正西")
    private String wind_direction_degree;
    @ApiModelProperty(value = "风速，单位为km/h公里每小时或mph英里每小时")
    private String wind_speed;
    @ApiModelProperty(value = "风力等级")
    private String wind_scale;
    @ApiModelProperty(value = "云量，单位%，范围0~100，天空被云覆盖的百分比 #目前不支持中国城市#")
    private String clouds;
    @ApiModelProperty(value = "露点温度 目前不支持中国城市")
    private String dew_point;


}