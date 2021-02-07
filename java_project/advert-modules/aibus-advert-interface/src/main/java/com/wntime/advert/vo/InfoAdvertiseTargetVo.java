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
public class InfoAdvertiseTargetVo {

    @JsonSerialize(using = LongToStringSerialize.class)
    private Long advertiseTargetId;
    /**
     * @desc 广告标签
     */
    private String advertiseTargetName;
    /**
     * @desc 广告分类编码
     * @value link、value、char
     */
    private String advertiseTargetGrope;
    /**
     * @desc 广告标签编码
     */
    private String advertiseTargetCode;

}
