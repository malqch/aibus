package com.wntime.ec.module.sys.dao;

import com.wntime.ec.common.util.mybatis.dao.BaseDao;
import com.wntime.ec.module.sys.entity.OrderAdvertiseDelivery;
import com.wntime.ec.module.sys.vo.OrderAdvertiseDeliveryQryReqVo;
import com.wntime.ec.module.sys.vo.OrderAdvertiseDeliveryQryRspVo;

import java.util.List;

public interface OrderAdvertiseDeliveryDao extends BaseDao<OrderAdvertiseDelivery, OrderAdvertiseDeliveryQryRspVo, OrderAdvertiseDeliveryQryReqVo> {

    List<Long> selectOrderAdvertiseDeliveryIds(OrderAdvertiseDeliveryQryReqVo orderAdvertiseDeliveryQryReqVo);

    Number selectCount(OrderAdvertiseDeliveryQryReqVo countParam);

}
