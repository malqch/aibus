package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @desc 订单交付表
 * @date 2020-08-25 14:04:05
 */
public interface OrderCompanyDeliveryService extends IService<OrderCompanyDeliveryEntity> {

    Map<Long,Long> saveImportOrderCompanyDeliveryInfo(Map<Long,Integer> map, long orderId, Map<Long,Timestamp> mapDate,long userId);

    PageUtils queryPage(Map<String, Object> params);

    List<OrderCompanyDeliveryEntity> getAllList(Long orderId, Integer isCompleted);

    List<OrderCompanyDeliveryEntity> getOrderCompanyDeliveryByOrderId(Long orderId);

    OrderCompanyDeliveryEntity getDetailById(Long id);

    void save(OrderCompanyDeliveryEntity orderCompanyDeliveryEntity, Long userId);

    void updateById(OrderCompanyDeliveryEntity orderCompanyDeliveryEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

