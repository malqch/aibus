package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IOrderAdvertiseDeliveryService;
import com.wntime.ec.module.sys.entity.OrderAdvertiseDelivery;
import com.wntime.ec.module.sys.dao.OrderAdvertiseDeliveryDao;
import com.wntime.ec.module.sys.vo.OrderAdvertiseDeliveryQryRspVo;
import com.wntime.ec.module.sys.vo.OrderAdvertiseDeliveryQryReqVo;

import java.util.List;

@Service
@Slf4j
public class OrderAdvertiseDeliveryServiceImpl extends BaseServiceImpl<OrderAdvertiseDeliveryDao,OrderAdvertiseDelivery, OrderAdvertiseDeliveryQryRspVo,OrderAdvertiseDeliveryQryReqVo> implements IOrderAdvertiseDeliveryService {

      @Autowired
      OrderAdvertiseDeliveryDao orderAdvertiseDeliveryDao;

      @Override
      public List<Long> selectOrderAdvertiseDeliveryIds(OrderAdvertiseDeliveryQryReqVo orderAdvertiseDeliveryQryReqVo) {
            return orderAdvertiseDeliveryDao.selectOrderAdvertiseDeliveryIds(orderAdvertiseDeliveryQryReqVo);
      }

      @Override
      public Number selectCount(OrderAdvertiseDeliveryQryReqVo countParam) {
            return orderAdvertiseDeliveryDao.selectCount(countParam);
      }
}
