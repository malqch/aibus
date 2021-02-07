package com.wntime.advert.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.OrderAdvertiseAttachDao;
import com.wntime.advert.entity.OrderAdvertiseAttachEntity;
import com.wntime.advert.service.OrderAdvertiseAttachService;


@Service("orderAdvertiseAttachService")
public class OrderAdvertiseAttachServiceImpl extends ServiceImpl<OrderAdvertiseAttachDao, OrderAdvertiseAttachEntity> implements OrderAdvertiseAttachService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderAdvertiseAttachEntity> page = this.page(
                new Query<OrderAdvertiseAttachEntity>().getPage(params),
                new QueryWrapper<OrderAdvertiseAttachEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteBatchByAdvertiseId(Long advertiseId) {
        getBaseMapper().delete(new QueryWrapper<OrderAdvertiseAttachEntity>().eq("advertise_delivery_id",advertiseId));
    }

    @Override
    public List<OrderAdvertiseAttachEntity> listByAdvertiseId(Long advertiseId) {
        return list(new QueryWrapper<OrderAdvertiseAttachEntity>().eq("advertise_delivery_id",advertiseId));
    }

}