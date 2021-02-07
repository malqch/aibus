package com.wntime.advert.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * @author 79448
 * @date 2020/11/6 16:56
 */
@Setter
@Getter
public class AdvertiseDeliveryDetailVo {
    private Long deliveryAreaId;
    /**
     * @desc 广告投放Id
     */
    private Long advertiseDeliveryId;
    /**
     * @desc 公交线路Id
     */
    private Long companyLineId;
    /**
     * @desc 线路车站Id
     */
    private Long lineStationId;

    private Long deliveryTargetId;

    /**
     * @desc 广告标签Id
     */
    private Long advertiseTargetId;

    /**
     * @desc 投放方式  line station
     */
    private String advertiseDeliveryType;
    /**
     * @desc 起始时间
     */

    private Date deliveryBegin;
    /**
     * @desc 截止时间
     */
    private Date deliveryEnd;
    /**
     * @desc 审核状态
     @values 0 草稿，1审核中，2，通过，3，投放中，4，未通过，9 下线
     */
    private Integer checkStatus;
    /**
     * @desc 审核意见
     */
    private String checkSuggest;
    /**
     * @desc 是否插播
     @values 0 不是，1 是
     */
    private Integer isInterrupt;
    /**
     * @desc 插播通知
     */
    private String interruptNotice;

    private String advertiseNo;

    private Long advertiseAttachId;

    /**
     * @desc 广告位Id
     */
    private Long advertisePositionId;
    /**
     * @desc 播放时长
     */
    private Double showTimes;
    /**
     * @desc 素材类型
     */
    private int attachType;
    /**
     * @desc 素材地址
     */
    private String attachLink;
}
