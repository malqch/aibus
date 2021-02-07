package com.wntime.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.entity.InfoEventTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 事件类型表
 *
 * @date 2020-08-25 13:34:24
 */
public interface InfoEventTypeService extends IService<InfoEventTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveEventType(InfoEventTypeEntity infoEventTypeEntity);

    void updateEventType(InfoEventTypeEntity infoEventTypeEntity);

    void deleteEventType(List<Long> eventTypeIds);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

