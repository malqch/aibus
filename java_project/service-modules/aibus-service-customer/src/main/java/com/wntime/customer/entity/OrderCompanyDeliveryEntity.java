package com.wntime.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @desc 订单交付表
 * @date 2020-08-25 14:04:05
 */
@Data
@TableName("order_company_delivery")
public class OrderCompanyDeliveryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @desc 主键
     */
    @TableId(value = "company_delivery_id", type = IdType.ID_WORKER)
    private Long companyDeliveryId;
    /**
     * @desc 订单Id
     */
    @NotNull(message = "所属订单不能为空")
    private Long orderId;

    //订单交付状态
    private Integer isCompleted;

    //订单描述
    @TableField(exist = false)
    private String orderDesc;
    /**
     * @desc 公交车型Id
     */
    @NotNull(message = "公交车型不能为空")
    private Long busTypeId;

    //公交车型名称
    @TableField(exist = false)
    private String busTypeName;
    /**
     * @desc 交付数量
     */
    @NotNull(message = "交付数量不能为空")
    @Positive(message = "交付数量要大于0")
    private Integer orderDeliveryNum;
    /**
     * @desc 交付日期
     */
    @NotNull(message = "交付日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp orderDeliveryDate;
    /**
     * @desc 说明
     */
    @Size(min = 0,max = 200,message = "交付说明长度应小于200")
    private String orderDetailDesc;
    /**
     * @desc 是否删除
     */
    private Integer isDeleted;
    /**
     * @desc 创建时间
     */
    private Timestamp createdDate;
    /**
     * @desc 修改时间
     */
    private Timestamp modifiedDate;

    @TableField(exist = false)
    private String createUserName;

    @TableField(exist = false)
    private String modifiedUserName;

}
