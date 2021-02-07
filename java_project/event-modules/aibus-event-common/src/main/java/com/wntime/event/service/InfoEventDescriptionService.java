package com.wntime.event.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.event.entity.InfoEventDescriptionEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @date 2020-09-01 15:22:13
 */
public interface InfoEventDescriptionService extends IService<InfoEventDescriptionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    InfoEventDescriptionEntity getDetailById(Long id);

        void save(InfoEventDescriptionEntity infoEventDescriptionEntity, Long userId);

        void updateById(InfoEventDescriptionEntity infoEventDescriptionEntity, Long userId);

        void delById(Long id, Long userId);

        void deleteBatch(String[] ids, Long userId);
}

