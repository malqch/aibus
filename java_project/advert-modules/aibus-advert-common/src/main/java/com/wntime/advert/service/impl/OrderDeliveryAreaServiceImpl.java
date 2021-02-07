package com.wntime.advert.service.impl;

import com.wntime.advert.vo.DeliveryLineVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.OrderDeliveryAreaDao;
import com.wntime.advert.entity.OrderDeliveryAreaEntity;
import com.wntime.advert.service.OrderDeliveryAreaService;


@Service("orderDeliveryAreaService")
public class OrderDeliveryAreaServiceImpl extends ServiceImpl<OrderDeliveryAreaDao, OrderDeliveryAreaEntity> implements OrderDeliveryAreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderDeliveryAreaEntity> page = this.page(
                new Query<OrderDeliveryAreaEntity>().getPage(params),
                new QueryWrapper<OrderDeliveryAreaEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteBatchByAdvertiseId(Long advertiseId) {
        getBaseMapper().delete(new QueryWrapper<OrderDeliveryAreaEntity>().eq("advertise_delivery_id",advertiseId));
    }

    @Override
    public List<OrderDeliveryAreaEntity> listByAdvertiseId(Long advertiseId) {
        return list(new QueryWrapper<OrderDeliveryAreaEntity>().eq("advertise_delivery_id",advertiseId));
    }

    @Override
    public List<String> listStationByAdvertiseId(Long advertiseId) {
        return getBaseMapper().listStationByAdvertiseId(advertiseId);
    }

    @Override
    public List<DeliveryLineVo> listLineCodeByAdvertise(Long advertiseId) {
        return getBaseMapper().listLineCodeByAdvertise(advertiseId);
    }
}