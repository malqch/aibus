package com.wntime.ec.module.sys.dao;

import com.wntime.ec.common.util.mybatis.dao.BaseDao;
import com.wntime.ec.module.sys.entity.OrderDeliveryTarget;
import com.wntime.ec.module.sys.vo.OrderDeliveryTargetQryReqVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryTargetQryRspVo;

import java.util.List;

public interface OrderDeliveryTargetDao extends BaseDao<OrderDeliveryTarget, OrderDeliveryTargetQryRspVo, OrderDeliveryTargetQryReqVo> {

    List<OrderDeliveryTargetQryRspVo> selectAdvertiseTargetList(OrderDeliveryTargetQryReqVo targetQryReqVo);

}
