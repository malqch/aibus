package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.wntime.customer.vo.OrderBusCompanyVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交订单表
 * @date 2020-08-25 14:04:05
 */
public interface OrderBusCompanyService extends IService<OrderBusCompanyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<OrderBusCompanyVo> getByOrderStatus(List<Long> companyIdList, Long companyId, Integer isCompleted);

    OrderBusCompanyVo getDetailById(Long id);

    OrderBusCompanyEntity getDetailByOrderCode(String orderCode);

    void save(OrderBusCompanyEntity orderBusCompanyEntity, Long userId);

    void updateById(OrderBusCompanyEntity orderBusCompanyEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

