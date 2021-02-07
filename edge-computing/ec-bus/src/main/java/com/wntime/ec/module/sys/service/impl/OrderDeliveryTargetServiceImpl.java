package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IOrderDeliveryTargetService;
import com.wntime.ec.module.sys.entity.OrderDeliveryTarget;
import com.wntime.ec.module.sys.dao.OrderDeliveryTargetDao;
import com.wntime.ec.module.sys.vo.OrderDeliveryTargetQryRspVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryTargetQryReqVo;

import java.util.List;

@Service
@Transactional
@Slf4j
public class OrderDeliveryTargetServiceImpl extends BaseServiceImpl<OrderDeliveryTargetDao,OrderDeliveryTarget, OrderDeliveryTargetQryRspVo,OrderDeliveryTargetQryReqVo> implements IOrderDeliveryTargetService {

      @Autowired
      OrderDeliveryTargetDao orderDeliveryTargetDao;

      @Override
      public List<OrderDeliveryTargetQryRspVo> selectAdvertiseTargetList(OrderDeliveryTargetQryReqVo targetQryReqVo) {
            return orderDeliveryTargetDao.selectAdvertiseTargetList(targetQryReqVo);
      }
}
