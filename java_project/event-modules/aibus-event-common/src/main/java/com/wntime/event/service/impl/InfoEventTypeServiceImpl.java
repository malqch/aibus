package com.wntime.event.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.event.dao.InfoEventTypeDao;
import com.wntime.event.entity.InfoEventTypeEntity;
import com.wntime.event.region.EventType;
import com.wntime.event.repo.EventTypeRepository;
import com.wntime.event.service.InfoEventTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("infoEventTypeService")
public class InfoEventTypeServiceImpl extends ServiceImpl<InfoEventTypeDao, InfoEventTypeEntity> implements InfoEventTypeService {

    @Resource
    private EventTypeRepository eventTypeRepository;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEventTypeEntity> page = new Query<InfoEventTypeEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.listWithUser(params));
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEventType(InfoEventTypeEntity infoEventTypeEntity) {
        save(infoEventTypeEntity);
        if(Constant.Deleted.UNDELETED.getValue()==infoEventTypeEntity.getIsDeleted()  &&
                Constant.Enabled.ENABLE.getValue()== infoEventTypeEntity.getIsEnabled()){
            saveEventTypeRegion(infoEventTypeEntity.getEventTypeId(),infoEventTypeEntity.getEventTypeName(),infoEventTypeEntity.getEventTypeCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEventType(InfoEventTypeEntity infoEventTypeEntity) {
        updateById(infoEventTypeEntity);
        if(infoEventTypeEntity.getIsEnabled()==1){
            saveEventTypeRegion(infoEventTypeEntity.getEventTypeId(),infoEventTypeEntity.getEventTypeName(),infoEventTypeEntity.getEventTypeCode());
        }else {
            eventTypeRepository.deleteById(infoEventTypeEntity.getEventTypeId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEventType(List<Long> eventTypeIds) {
        removeByIds(eventTypeIds);
        for(Long eventTypeId:eventTypeIds){
            eventTypeRepository.deleteById(eventTypeId);
        }
    }

    public void saveEventTypeRegion(long eventTypeId,String eventTypeName,String eventTypeCode){
        EventType eventType = new EventType();
        eventType.setEventTypeId(eventTypeId);
        eventType.setEventTypeName(eventTypeName);
        eventType.setEventTypeCode(eventTypeCode);
        eventTypeRepository.save(eventType);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoEventTypeEntity entity = new InfoEventTypeEntity();
        entity.setEventTypeId(id);
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
