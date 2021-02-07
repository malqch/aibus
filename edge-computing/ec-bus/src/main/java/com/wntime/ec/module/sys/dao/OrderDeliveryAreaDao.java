package com.wntime.ec.module.sys.dao;

import com.wntime.ec.common.util.mybatis.dao.BaseDao;
import com.wntime.ec.module.sys.entity.OrderDeliveryArea;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryReqVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryRspVo;

import java.util.List;
import java.util.Map;

public interface OrderDeliveryAreaDao extends BaseDao<OrderDeliveryArea, OrderDeliveryAreaQryRspVo, OrderDeliveryAreaQryReqVo> {

    List<Long> selectAdvertiseDeliveryList(Map param);

}
