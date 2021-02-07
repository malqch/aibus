package com.wntime.advert.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wntime.util.LongToStringSerialize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 79448
 * @date 2020/11/6 16:01
 */
@Setter
@Getter
public class InfoAdvertisePositionVo {
    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertisePositionId;
    /**
     * @desc 广告位描述
     */
    private String positionDesc;
    /**
     * @desc 广告位编码
     */
    private String positionCode;
    /**
     * @desc 广告位分类
     */
    private String positionGroup;
    /**
     * @desc 像素高
     */
    private Double pixelHeight;
    /**
     * @desc 像素宽
     */
    private Double pixelWidth;
    /**
     * @desc 屏幕高
     */
    private Double screenHeight;
    /**
     * @desc 屏幕宽
     */
    private Double screenWidth;
    /**
     * @desc 素材类型
     @values 0 单图片；1 单视频；2 全可以
     */
    private Integer advertiseType;
}
