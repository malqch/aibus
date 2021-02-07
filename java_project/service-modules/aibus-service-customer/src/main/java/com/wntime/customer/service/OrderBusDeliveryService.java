package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.OrderBusDeliveryEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.customer.vo.OrderBusDeliveryVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 车辆交付表
 * @date 2020-08-25 14:00:24
 */
public interface OrderBusDeliveryService extends IService<OrderBusDeliveryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderBusDeliveryVo getDetailById(Long id);

    void saveImportBatch(List<OrderBusDeliveryBatchForm> orderBusDeliveryBatchForms,long userId);

    void save(OrderBusDeliveryEntity orderBusDeliveryEntity, Long userId);

    void updateById(OrderBusDeliveryEntity orderBusDeliveryEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

