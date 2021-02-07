package com.wntime.advert.service.impl;

import com.wntime.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.InfoAdvertiseTargetDao;
import com.wntime.advert.entity.InfoAdvertiseTargetEntity;
import com.wntime.advert.service.InfoAdvertiseTargetService;
import org.springframework.transaction.annotation.Transactional;


@Service("infoAdvertiseTargetService")
public class InfoAdvertiseTargetServiceImpl extends ServiceImpl<InfoAdvertiseTargetDao, InfoAdvertiseTargetEntity> implements InfoAdvertiseTargetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoAdvertiseTargetEntity> page = new Query<InfoAdvertiseTargetEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.listWithUser(params));
        return new PageUtils(page);
    }

    @Override
    public List<InfoAdvertiseTargetEntity> listByGroup(String group) {
        return getBaseMapper().listByGroup(group);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoAdvertiseTargetEntity entity = new InfoAdvertiseTargetEntity();
        entity.setAdvertiseTargetId(id);
        entity.setIsDeleted(1);
        entity.setModifiedBy(userId);
        entity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public List<InfoAdvertiseTargetEntity> listByAdvertiseIdAndGroup(Long advertiseId, String group) {
        return getBaseMapper().listByAdvertiseIdAndGroup(advertiseId,group);
    }

}