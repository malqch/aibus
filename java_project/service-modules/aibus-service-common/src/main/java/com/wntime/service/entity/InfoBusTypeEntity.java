package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @desc 公交车型
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_bus_type")
public class InfoBusTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "bus_type_id", type = IdType.ID_WORKER)
    private Long busTypeId;
    /**
     * @desc 电机型号Id
     */
//    @NotNull(message = "电机型号不能为空")
    private Long motorTypeId;

    //电机型号名称
    @TableField(exist = false)
    private String motorTypeName;
    /**
     * @desc 电池类型Id
     */
//    @NotNull(message = "电池型号不能为空")
    private Long batteryTypeId;

    //电池类型名称
    @TableField(exist = false)
    private String batteryTypeName;
    /**
     * @desc 公交车型名称
     */
    @NotNull(message = "车型名称不能为空")
    @Size(min=0,max = 20,message = "车型名称不能超过20个字符")
    private String busTypeName;
    /**
     * @desc 公交车型编码
     */
    @NotNull(message = "车型编码不能为空")
    @Size(min=0,max = 20,message = "车型编码不能超过20个字符")
    private String busTypeCode;
    /**
     * @desc 公交车型照片
     */
    private String busTypeImage;

    @TableField(exist = false)
    private String busTypeImageUrl;

    @TableField(exist = false)
    @JsonIgnore
    private MultipartFile busTypeImageFile;

    /**
     * @desc 车型说明
     */
    @Size(min=0,max = 128,message = "车型说明不能超过128个字符")
    private String busTypeDesc;
    /**
     * @desc 质保里程
     */
//    @NotNull(message = "质保里程不能为空")
    @Positive(message = "质保里程必须大于0" )
    private Integer warrantyMileage;
    /**
     * @desc 质保年数
     */
//    @NotNull(message = "质保年数不能为空")
    @Positive(message = "质保年数必须大于0" )
    private Integer warrantyYears;
    /**
     * @desc 核载人数
     */
    @NotNull(message = "核载人数不能为空")
    @Positive(message = "核载人数必须大于0" )
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

    @TableField(exist = false)
    private String createUserName;

    @TableField(exist = false)
    private String modifiedUserName;

    @Override
    public String toString() {
        return "InfoBusTypeEntity{" +
                "busTypeId=" + busTypeId +
                ", motorTypeId=" + motorTypeId +
                ", motorTypeName='" + motorTypeName + '\'' +
                ", batteryTypeId=" + batteryTypeId +
                ", batteryTypeName='" + batteryTypeName + '\'' +
                ", busTypeName='" + busTypeName + '\'' +
                ", busTypeCode='" + busTypeCode + '\'' +
                ", busTypeImage='" + busTypeImage + '\'' +
                ", busTypeImageFile=" + busTypeImageFile +
                ", busTypeDesc='" + busTypeDesc + '\'' +
                ", warrantyMileage=" + warrantyMileage +
                ", warrantyYears=" + warrantyYears +
                ", peopleNumber=" + peopleNumber +
                ", isDeleted=" + isDeleted +
                ", isEnabled=" + isEnabled +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", modifiedBy=" + modifiedBy +
                ", modifiedDate=" + modifiedDate +
                ", createUserName='" + createUserName + '\'' +
                ", modifiedUserName='" + modifiedUserName + '\'' +
                '}';
    }
}
