package com.wntime.customer.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 公交订单表VO
 */
@Data
public class OrderBusCompanyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;

    //订单编码
    private String orderCode;

    //车厂Id
    private String factoryId;

    //车厂名称
    private String factoryName;

    //车厂招牌
    private String factorySnapshot;

    //公交公司Id
    private Long companyId;

    private Long sellerId;

    private String sellerName;

    //公交公司名称
    private String companyName;

    //公交公司图标
    private String companySnapshot;

    private String companyCode;

    /**
     * @desc 经营范围
     */
    private String companyScope;

    /**
     * @desc 公司地址
     */
    private String companyAddress;

    /**
     * @desc 联系人
     */
    private String companyPerson;

    /**
     * @desc 电话
     */
    private String companyPhone;

    /**
     * @desc 邮箱
     */
    private String companyEmail;

    //签订时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp orderDate;

    //是否删除
    private Integer isDeleted;

    //创建人
    private Long createdBy;

    //创建时间
    private Timestamp createdDate;

    //修改人
    private Long modifiedBy;

    //修改时间
    private Timestamp modifiedDate;

    private String createUserName;

    private String modifiedUserName;

    private Integer orderDetailNum;

    private Integer orderDeliveryNum;

}
