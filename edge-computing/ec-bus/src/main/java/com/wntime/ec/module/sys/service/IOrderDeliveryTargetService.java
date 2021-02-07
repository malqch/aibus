package com.wntime.ec.module.sys.service;

import com.wntime.ec.common.util.mybatis.service.IBaseService;
import com.wntime.ec.module.sys.entity.OrderDeliveryTarget;
import com.wntime.ec.module.sys.vo.OrderDeliveryTargetQryReqVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryTargetQryRspVo;

import java.util.List;

public interface IOrderDeliveryTargetService extends IBaseService<OrderDeliveryTarget, OrderDeliveryTargetQryRspVo, OrderDeliveryTargetQryReqVo> {

    List<OrderDeliveryTargetQryRspVo> selectAdvertiseTargetList(OrderDeliveryTargetQryReqVo targetQryReqVo);

}
