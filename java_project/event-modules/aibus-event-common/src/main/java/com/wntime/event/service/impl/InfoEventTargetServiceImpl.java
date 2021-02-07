package com.wntime.event.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.event.dao.InfoEventTargetDao;
import com.wntime.event.entity.InfoEventTargetEntity;
import com.wntime.event.entity.InfoEventTypeEntity;
import com.wntime.event.service.InfoEventTargetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("infoEventTargetService")
public class InfoEventTargetServiceImpl extends ServiceImpl<InfoEventTargetDao, InfoEventTargetEntity> implements InfoEventTargetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEventTargetEntity> page = new Query<InfoEventTargetEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.listWithUser(params));
        return new PageUtils(page);
    }

    @Override
    public List<InfoEventTargetEntity> getTrafficEventTypeList() {

        return getBaseMapper().getTrafficEventTypeList();
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoEventTargetEntity entity = new InfoEventTargetEntity();
        entity.setEventTargetId(id);
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

}
