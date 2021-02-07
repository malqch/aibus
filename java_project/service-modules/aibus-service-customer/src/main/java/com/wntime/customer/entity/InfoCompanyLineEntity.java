package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * @desc 公交线路表
 * @date 2020-08-25 14:04:05
 */
@Data
@TableName("info_company_line")
public class InfoCompanyLineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "company_line_id", type = IdType.ID_WORKER)
    private Long companyLineId;
    /**
     * @desc 公交公司Id
     */
    @NotNull(message = "所属公交公司不能为空")
    private Long companyId;

    @TableField(exist = false)
    private String companyName;

//    @NotNull(message = "线路类型不能为空")
    private Long lineType;

    @NotNull(message = "线路方向不能为空")
    private String direction;

    @TableField(exist = false)
    private String directionName;

    @NotNull(message = "学校不能为空")
    private Long schoolId;

    @TableField(exist = false)
    private String lineTypeShow;

    @NotNull(message = "线路里程不能为空")
    private double lineMileage;

    //公交车站
    @TableField(exist = false)
    private Long[] busStationIds;

    @TableField(exist = false)
    private List<InfoBusStationEntity> busStationList;
    /**
     * @desc 公交线路名称
     */
    @NotBlank(message = "公交线路名称不能为空")
    @Size(min = 0,max = 20,message = "公交线路名称长度应小于20")
    private String companyLineName;
    /**
     * @desc 公交线路编码
     */
    @NotBlank(message = "公交线路编码不能为空")
    @Size(min = 0,max = 20,message = "公交线路编码长度应小于20")
    private String companyLineCode;
    /**
     * @desc 夏季开始时间
     */
    @NotNull(message = "夏季开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp summerStartTime;
    /**
     * @desc 夏季结束时间
     */
    @NotNull(message = "夏季结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp summerEndTime;
    /**
     * @desc 夏季首班时间
     */
    @NotNull(message = "夏季首班时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp summerFirstTime;
    /**
     * @desc 夏季末班时间
     */
    @NotNull(message = "夏季末班时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp summerLastTime;
    /**
     * @desc 冬季首班时间
     */
    @NotNull(message = "冬季首班时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp winterFirstTime;
    /**
     * @desc 冬季末班时间
     */
    @NotNull(message = "冬季末班时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp winterLastTime;
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

}
