package com.wntime.event.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.event.dao.InfoEventLevelDao;
import com.wntime.event.entity.InfoEventLevelEntity;
import com.wntime.event.service.InfoEventLevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service("infoEventLevelService")
public class InfoEventLevelServiceImpl extends ServiceImpl<InfoEventLevelDao, InfoEventLevelEntity> implements InfoEventLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEventLevelEntity> page = new Query<InfoEventLevelEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.listWithUser(params));
        return new PageUtils(page);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoEventLevelEntity infoEventLevelEntity = new InfoEventLevelEntity();
        infoEventLevelEntity.setEventLevelId(id);
        infoEventLevelEntity.setIsDeleted(1);
        infoEventLevelEntity.setModifiedBy(userId);
        infoEventLevelEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoEventLevelEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }
}
