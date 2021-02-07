package com.wntime.customer.dao;

import com.wntime.customer.entity.OrderBusCompanyDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author psp
 * @desc 订单详情表
 * @date 2020-09-04 15:19:14
 */
@Mapper
public interface OrderBusCompanyDetailDao extends BaseMapper<OrderBusCompanyDetailEntity> {

    List<OrderBusCompanyDetailEntity> queryPageList(Map<String, Object> params);

    List<OrderBusCompanyDetailEntity> getOrderBusCompanyDetailByOrderId(Long orderId);

}
