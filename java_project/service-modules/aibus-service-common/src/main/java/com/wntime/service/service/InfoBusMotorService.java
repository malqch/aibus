package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoBusBatteryEntity;
import com.wntime.service.entity.InfoBusMotorEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 电机表
 * @date 2020-09-01 10:05:27
 */
public interface InfoBusMotorService extends IService<InfoBusMotorEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoBusMotorEntity> queryListByBusId(Map<String, Object> params);

    InfoBusMotorEntity getDetailById(Long id);

    void save(InfoBusMotorEntity infoBusMotorEntity, Long userId);

    void updateById(InfoBusMotorEntity infoBusMotorEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

