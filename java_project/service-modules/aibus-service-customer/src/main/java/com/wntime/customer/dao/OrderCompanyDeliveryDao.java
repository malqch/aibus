package com.wntime.customer.dao;

import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Mark
 * @desc 订单交付表
 * @date 2020-08-25 14:04:05
 */
@Mapper
public interface OrderCompanyDeliveryDao extends BaseMapper<OrderCompanyDeliveryEntity> {
    List<OrderCompanyDeliveryEntity> queryPageList(Map<String, Object> params);

    List<OrderCompanyDeliveryEntity> getAllList(Long orderId,Integer isCompleted);

    List<OrderCompanyDeliveryEntity> getOrderCompanyDeliveryByOrderId(Long orderId);

    int queryCompleteDeliveryBusSum(@Param("factoryId")long factoryId);

    int queryOrderBusSum(@Param("factoryId")long factoryId);
}
