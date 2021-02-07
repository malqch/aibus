package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.OrderBusCompanyDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 订单详情表
 * @date 2020-09-04 15:19:14
 */
public interface OrderBusCompanyDetailService extends IService<OrderBusCompanyDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<OrderBusCompanyDetailEntity> getOrderBusCompanyDetailByOrderId(Long orderId);

    OrderBusCompanyDetailEntity getDetailById(Long id);

    void save(OrderBusCompanyDetailEntity orderBusCompanyDetailEntity, Long userId);

    void updateById(OrderBusCompanyDetailEntity orderBusCompanyDetailEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

