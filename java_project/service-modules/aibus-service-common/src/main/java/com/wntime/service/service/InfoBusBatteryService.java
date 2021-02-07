package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoBusBatteryEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 电池表
 * @date 2020-08-25 14:28:17
 */
public interface InfoBusBatteryService extends IService<InfoBusBatteryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoBusBatteryEntity> queryListByBusId(Map<String, Object> params);

    InfoBusBatteryEntity getDetailById(Long id);

    void save(InfoBusBatteryEntity infoBusBatteryEntity, Long userId);

    void updateById(InfoBusBatteryEntity infoBusBatteryEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

