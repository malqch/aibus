package com.wntime.event.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.event.dao.InfoCollectEventDao;
import com.wntime.event.entity.InfoCollectEventEntity;
import com.wntime.event.entity.InfoEventTypeEntity;
import com.wntime.event.service.InfoCollectEventService;
import com.wntime.event.service.InfoEventDescriptionService;
import com.wntime.event.service.InfoEventExtendService;
import com.wntime.event.vo.InfoCollectEventDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service("infoCollectEventService")
public class InfoCollectEventServiceImpl extends ServiceImpl<InfoCollectEventDao, InfoCollectEventEntity> implements InfoCollectEventService {

    @Autowired
    private InfoEventExtendService infoEventExtendService;
    @Autowired
    private InfoEventDescriptionService infoEventDescriptionService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEventTypeEntity> page = new Query<InfoEventTypeEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.listDetail(params));
        return new PageUtils(page);
    }

    @Override
    public InfoCollectEventDetailVo getDetailById(Long collectEventId){
        return getBaseMapper().getDetailById(collectEventId);
    }

    @Override
    public InfoCollectEventDetailVo getDetailWithExtendById(Long collectEventId){
        InfoCollectEventDetailVo entityDetail = getBaseMapper().getDetailById(collectEventId);
        Assert.notNull(entityDetail,"没有对应数据");
        entityDetail.setEventExtendList(infoEventExtendService.listDetail(collectEventId));
        return entityDetail;
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoCollectEventEntity entity = new InfoCollectEventEntity();
        InfoCollectEventDetailVo eventDetailVo = getDetailById(id);
        entity.setCollectEventId(id);
        entity.setIsDeleted(1);
        entity.setModifiedBy(userId);
        entity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(entity);
        infoEventDescriptionService.delById(eventDetailVo.getEventDescriptionId(),userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }
}
