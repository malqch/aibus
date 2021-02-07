package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoBatteryTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 电池类型表
 * @date 2020-08-31 14:57:41
 */
public interface InfoBatteryTypeService extends IService<InfoBatteryTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoBatteryTypeEntity getDetailById(Long id);

    List<InfoBatteryTypeEntity> getAll();

    void save(InfoBatteryTypeEntity infoBatteryTypeEntity, Long userId);

    void updateById(InfoBatteryTypeEntity infoBatteryTypeEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

