package com.wntime.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 配置参数表
 * @date 2020-08-25 14:28:17
 */
@Data
@TableName("info_config_param")
public class InfoConfigParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "config_param_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long configParamId;
    /**
     * @desc 参数名称
     */
    private String paramName;
    /**
     * @desc 参数编码
     */
    private String paramCode;
    /**
     * @desc 参数分组
     */
    private String paramGroup;
    /**
     * @desc 数值
     */
    private Double paramValue;
    /**
     * @desc 字符
     */
    private String paramChar;
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
