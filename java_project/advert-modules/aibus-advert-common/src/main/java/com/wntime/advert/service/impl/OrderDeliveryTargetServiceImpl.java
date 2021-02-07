package com.wntime.advert.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.OrderDeliveryTargetDao;
import com.wntime.advert.entity.OrderDeliveryTargetEntity;
import com.wntime.advert.service.OrderDeliveryTargetService;


@Service("orderDeliveryTargetService")
public class OrderDeliveryTargetServiceImpl extends ServiceImpl<OrderDeliveryTargetDao, OrderDeliveryTargetEntity> implements OrderDeliveryTargetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderDeliveryTargetEntity> page = this.page(
                new Query<OrderDeliveryTargetEntity>().getPage(params),
                new QueryWrapper<OrderDeliveryTargetEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteBatchByAdvertiseId(Long advertiseId) {
        getBaseMapper().delete(new QueryWrapper<OrderDeliveryTargetEntity>().eq("advertise_delivery_id",advertiseId));
    }

    @Override
    public List<OrderDeliveryTargetEntity> listByAdvertiseId(Long advertiseId) {
        return list(new QueryWrapper<OrderDeliveryTargetEntity>().eq("advertise_delivery_id",advertiseId));
    }

    @Override
    public List<OrderDeliveryTargetEntity> listByAdvertiseIdAndGroup(Long advertiseId,String group) {
        return getBaseMapper().listByAdvertiseIdAndGroup(advertiseId,group);
    }

}