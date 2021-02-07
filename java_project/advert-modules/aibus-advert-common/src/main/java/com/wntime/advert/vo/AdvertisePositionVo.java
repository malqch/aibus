package com.wntime.advert.vo;

import com.wntime.advert.entity.InfoAdvertisePositionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ysc
 * 2020/11/6 15:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdvertisePositionVo extends InfoAdvertisePositionEntity {

    private Integer videoFileSize;
    private Integer pictureFileSize;

    private String videoFileSizeStr;
    private String pictureFileSizeStr;

    private String videoFileType;
    private String pictureFileType;

    private Integer videoFileTime;
    private String videoFileTimeStr;

    private Integer pictureFileTime;
    private String pictureFileTimeStr;

    private Integer fileNum;

}
