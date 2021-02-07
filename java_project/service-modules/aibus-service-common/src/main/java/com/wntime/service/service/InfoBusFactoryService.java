package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoBusFactoryEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交车厂表
 *
 * @date 2020-08-25 14:28:17
 */
public interface InfoBusFactoryService extends IService<InfoBusFactoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoBusFactoryEntity> getAll();

    InfoBusFactoryEntity getDetailById(Long id);

    void save(InfoBusFactoryEntity infoConfigParam, Long userId);

    void updateById(InfoBusFactoryEntity infoBusFactoryEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

