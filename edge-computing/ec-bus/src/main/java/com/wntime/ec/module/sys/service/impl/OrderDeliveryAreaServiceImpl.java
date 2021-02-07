package com.wntime.ec.module.sys.service.impl;

import com.wntime.ec.common.util.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wntime.ec.module.sys.service.IOrderDeliveryAreaService;
import com.wntime.ec.module.sys.entity.OrderDeliveryArea;
import com.wntime.ec.module.sys.dao.OrderDeliveryAreaDao;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryRspVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryReqVo;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderDeliveryAreaServiceImpl extends BaseServiceImpl<OrderDeliveryAreaDao,OrderDeliveryArea, OrderDeliveryAreaQryRspVo,OrderDeliveryAreaQryReqVo> implements IOrderDeliveryAreaService {

      @Autowired
      OrderDeliveryAreaDao orderDeliveryAreaDao;

      @Override
      public List<Long> selectAdvertiseDeliveryList(Map param) {
            return orderDeliveryAreaDao.selectAdvertiseDeliveryList(param);
      }
}
