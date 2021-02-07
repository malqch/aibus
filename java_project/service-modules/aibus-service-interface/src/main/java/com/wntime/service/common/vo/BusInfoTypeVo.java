package com.wntime.service.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @desc 公交车型
 * @date 2020-08-25 14:28:17
 */
@Data
public class BusInfoTypeVo {
    /**
     * @desc 主键
     */
    private Long busTypeId;
    /**
     * @desc 电机型号Id
     */
    private Long motorTypeId;

    /**
     * @desc 电池类型Id
     */
    private Long batteryTypeId;

    private String busTypeName;
    /**
     * @desc 公交车型编码
     */
    private String busTypeCode;
    /**
     * @desc 公交车型照片
     */
    private String busTypeImage;

    /**
     * @desc 车型说明
     */
    private String busTypeDesc;
    /**
     * @desc 质保里程
     */
    private Integer warrantyMileage;
    /**
     * @desc 质保年数
     */
    private Integer warrantyYears;
    /**
     * @desc 核载人数
     */
    private Integer peopleNumber;
    /**
     * @desc 是否删除
     */
    private Integer isDeleted;
    /**
     * @desc 是否启用
     */
    private Integer isEnabled;
    /**
     * @desc 创建人
     */
    private Long createdBy;
    /**
     * @desc 创建时间
     */
    private Timestamp createdDate;
    /**
     * @desc 修改人
     */
    private Long modifiedBy;
    /**
     * @desc 修改时间
     */
    private Timestamp modifiedDate;
}
