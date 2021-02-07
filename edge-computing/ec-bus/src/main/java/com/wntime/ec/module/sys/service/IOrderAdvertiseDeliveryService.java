package com.wntime.ec.module.sys.service;

import com.wntime.ec.common.util.mybatis.service.IBaseService;
import com.wntime.ec.module.sys.entity.OrderAdvertiseDelivery;
import com.wntime.ec.module.sys.vo.OrderAdvertiseDeliveryQryReqVo;
import com.wntime.ec.module.sys.vo.OrderAdvertiseDeliveryQryRspVo;

import java.util.List;

public interface IOrderAdvertiseDeliveryService extends IBaseService<OrderAdvertiseDelivery, OrderAdvertiseDeliveryQryRspVo, OrderAdvertiseDeliveryQryReqVo> {

    List<Long> selectOrderAdvertiseDeliveryIds(OrderAdvertiseDeliveryQryReqVo orderAdvertiseDeliveryQryReqVo);

    Number selectCount(OrderAdvertiseDeliveryQryReqVo countParam);

}
