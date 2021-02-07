package com.wntime.ec.module.sys.service;

import com.wntime.ec.common.util.mybatis.service.IBaseService;
import com.wntime.ec.module.sys.entity.OrderDeliveryArea;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryReqVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryRspVo;

import java.util.List;
import java.util.Map;

public interface IOrderDeliveryAreaService extends IBaseService<OrderDeliveryArea, OrderDeliveryAreaQryRspVo, OrderDeliveryAreaQryReqVo> {

    List<Long> selectAdvertiseDeliveryList(Map param);


}
