package com.wntime.service.common.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wntime.common.validator.group.AddGroup;
import com.wntime.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class OrderBusDeliveryBatchForm {

    /**
     * @desc 订单编码
     */
    private String orderCode;

    private Long orderId;

    private Long busId;
    /**
     * 车型编码
     */
    private String busTypeCode;

    private Long busTypeId;

    /**
     * 车辆VIN码
     */
    private String vinCode;

    /**
     * 车牌号
     */
    private String plateCode;

    /**
     *车辆编码
     */
    private String busCode;

    /**
     * @desc 交付日期
     */
    private Timestamp orderDeliveryDate;

    /**
     * @desc 交付说明
     */
    private String orderDeliveryDesc;

    /**
     * @desc 出保日期
     */
    private Timestamp orderOutDate;
    /**
     * @desc 出保说明
     */
    private String orderOutDesc;

    /**
     * 电机型号
     */
    private String motorTypeName;

    private Long motorTypeId;

    /**
     * 车辆电机编码
     */
    private String busMotorCode;

    /**
     * 电池型号
     */
    private String batteryTypeName;

    private Long batteryTypeId;

    /**
     * 车辆电池编码
     */
    private List<String> busBatteryCodes;

    private long userId;

}