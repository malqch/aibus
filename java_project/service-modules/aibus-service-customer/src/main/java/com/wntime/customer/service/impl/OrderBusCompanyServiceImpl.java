package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.customer.dao.OrderBusCompanyDao;
import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.wntime.customer.entity.OrderCompanyDeliveryEntity;
import com.wntime.customer.service.OrderBusCompanyService;
import com.wntime.customer.service.OrderCompanyDeliveryService;
import com.wntime.customer.vo.OrderBusCompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交订单表
 *
 * @date 2020-09-04 10:03:34
 */
@Service("orderBusCompanyService")
public class OrderBusCompanyServiceImpl extends ServiceImpl<OrderBusCompanyDao, OrderBusCompanyEntity> implements OrderBusCompanyService {

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
        Page<OrderBusCompanyVo> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<OrderBusCompanyVo> getByOrderStatus(List<Long> companyIdList,Long companyId, Integer isCompleted) {
        return this.baseMapper.getByOrderStatus(companyIdList,companyId,isCompleted);
    }

    @Override
    public OrderBusCompanyVo getDetailById(Long id) {
        return this.baseMapper.getDetailById(id);
    }

    @Override
    public OrderBusCompanyEntity getDetailByOrderCode(String orderCode) {
        return this.getOne(new QueryWrapper<OrderBusCompanyEntity>()
                .eq("is_deleted", 0)
                .eq("order_code",orderCode));
    }

    @Override
    public void save(OrderBusCompanyEntity orderBusCompanyEntity, Long userId) {
        orderBusCompanyEntity.setOrderId(null);
        orderBusCompanyEntity.setIsDeleted(0);
        orderBusCompanyEntity.setCreatedDate(DateUtils.getTimestamp());
        orderBusCompanyEntity.setCreatedBy(userId);
        this.save(orderBusCompanyEntity);
    }

    @Override
    public void updateById(OrderBusCompanyEntity orderBusCompanyEntity, Long userId) {
        List<OrderCompanyDeliveryEntity> list = orderCompanyDeliveryService.list(new QueryWrapper<OrderCompanyDeliveryEntity>()
                .eq("order_id", orderBusCompanyEntity.getOrderId())
                .eq("is_deleted", 0)
        );
        if(!list.isEmpty()){
            throw new RRException("订单已经交付，不能修改！");
        }
        orderBusCompanyEntity.setModifiedBy(userId);
        orderBusCompanyEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(orderBusCompanyEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        List<OrderCompanyDeliveryEntity> list = orderCompanyDeliveryService.list(new QueryWrapper<OrderCompanyDeliveryEntity>()
                .eq("order_id", id)
                .eq("is_deleted", 0)
        );
        if(!list.isEmpty()){
            throw new RRException("订单下还有已交付订单，不能删除！");
        }

        OrderBusCompanyEntity orderBusCompanyEntity = new OrderBusCompanyEntity();
        orderBusCompanyEntity.setOrderId(id);
        orderBusCompanyEntity.setIsDeleted(1);
        orderBusCompanyEntity.setModifiedBy(userId);
        orderBusCompanyEntity.setModifiedDate(DateUtils.getTimestamp());

        this.updateById(orderBusCompanyEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

}
