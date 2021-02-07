package com.wntime.advert.service.impl;

import com.wntime.advert.vo.ItemVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.LogCheckItemDao;
import com.wntime.advert.entity.LogCheckItemEntity;
import com.wntime.advert.service.LogCheckItemService;


@Service("logCheckItemService")
public class LogCheckItemServiceImpl extends ServiceImpl<LogCheckItemDao, LogCheckItemEntity> implements LogCheckItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogCheckItemEntity> page = this.page(
                new Query<LogCheckItemEntity>().getPage(params),
                new QueryWrapper<LogCheckItemEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ItemVo> listByAdvertise(Long advertiseId) {
        return getBaseMapper().listByAdvertise(advertiseId);
    }

    @Override
    public void deleteBatchByAdvertiseId(Long advertiseId) {
        getBaseMapper().delete(new QueryWrapper<LogCheckItemEntity>().eq("advertise_delivery_id",advertiseId));
    }

    @Override
    public List<LogCheckItemEntity> listByAdvertiseId(Long advertiseId) {
        return list(new QueryWrapper<LogCheckItemEntity>().eq("advertise_delivery_id",advertiseId));
    }
}