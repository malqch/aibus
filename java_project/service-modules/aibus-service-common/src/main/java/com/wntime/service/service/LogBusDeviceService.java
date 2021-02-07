package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.LogBusDeviceEntity;

import java.util.Map;

/**
 * @desc 设备日志表
 *
 * @date 2020-09-02 13:49:03
 */
public interface LogBusDeviceService extends IService<LogBusDeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);
    LogBusDeviceEntity getDetailById(Long id);

        void save(LogBusDeviceEntity logBusDeviceEntity, Long userId);

        void updateById(LogBusDeviceEntity logBusDeviceEntity, Long userId);

}

