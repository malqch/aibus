package com.wntime.advert.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ysc
 * 2020/11/6 9:45
 */
@Data
public class AdvertiseAttachVo {

    private Long advertisePositionId;
    private List<String> fileList;
    private List<String> fileUrlList;
    private int advertiseType;
}
