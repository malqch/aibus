package com.wntime.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


// 车辆交付
@Data
public class OrderBusDeliveryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long busDeliveryId;

    // 公交车Id
    private Long busId;

    private String plateCode;

    private String vinCode;

    private String busCode;

    // 订单交付Id
    private Long companyDeliveryId;

    //订单ID
    private String orderId;

    //订单编号
    private String orderCode;

    //车型
    private String busTypeName;

    //公交公司
    private String companyName;

    // 交付日期
    private Timestamp orderDeliveryDate;

    // 交付说明
    private String orderDeliveryDesc;

    // 出保日期
    private Timestamp orderOutDate;

    // 出保说明
    private String orderOutDesc;

    // 是否删除
    private Integer isDeleted;

    // 创建时间
    private Timestamp createdDate;

    // 修改时间
    private Timestamp modifiedDate;

    private Integer orderDeliveryNum;

}
