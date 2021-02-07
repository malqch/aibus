package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.exception.RRException;
import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.wntime.customer.service.OrderCompanyDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.dao.OrderBusCompanyDetailDao;
import com.wntime.customer.entity.OrderBusCompanyDetailEntity;
import com.wntime.customer.service.OrderBusCompanyDetailService;

/**
 * @desc 订单详情表
 *
 * @date 2020-09-04 15:19:14
 */
@Service("orderBusCompanyDetailService")
public class OrderBusCompanyDetailServiceImpl extends ServiceImpl<OrderBusCompanyDetailDao, OrderBusCompanyDetailEntity> implements OrderBusCompanyDetailService {

    @Autowired
    private OrderCompanyDeliveryService orderCompanyDeliveryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }
        Page<OrderBusCompanyDetailEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<OrderBusCompanyDetailEntity> getOrderBusCompanyDetailByOrderId(Long orderId) {
        return this.baseMapper.getOrderBusCompanyDetailByOrderId(orderId);
    }

    @Override
    public OrderBusCompanyDetailEntity getDetailById(Long id) {
        return this.getById(id);
    }

    private void checkIfDelivery(Long orderId){
        List<OrderCompanyDeliveryEntity> list = orderCompanyDeliveryService.list(new QueryWrapper<OrderCompanyDeliveryEntity>()
                .eq("order_id", orderId)
                .eq("is_deleted", 0)
        );
        if(!list.isEmpty()){
            throw new RRException("订单已经交付，不能进行操作！");
        }
    }
    @Override
    public void save(OrderBusCompanyDetailEntity orderBusCompanyDetailEntity, Long userId) {
        checkIfDelivery(orderBusCompanyDetailEntity.getOrderId());
        orderBusCompanyDetailEntity.setOrderDetailId(null);
        orderBusCompanyDetailEntity.setIsDeleted(0);
        orderBusCompanyDetailEntity.setCreatedDate(DateUtils.getTimestamp());
        this.save(orderBusCompanyDetailEntity);
    }

    @Override
    public void updateById(OrderBusCompanyDetailEntity orderBusCompanyDetailEntity, Long userId) {
        checkIfDelivery(orderBusCompanyDetailEntity.getOrderId());
        orderBusCompanyDetailEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(orderBusCompanyDetailEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        checkIfDelivery(getDetailById(id).getOrderId());

        OrderBusCompanyDetailEntity orderBusCompanyDetailEntity = new OrderBusCompanyDetailEntity();
        orderBusCompanyDetailEntity.setOrderDetailId(id);
        orderBusCompanyDetailEntity.setIsDeleted(1);
        orderBusCompanyDetailEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(orderBusCompanyDetailEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
