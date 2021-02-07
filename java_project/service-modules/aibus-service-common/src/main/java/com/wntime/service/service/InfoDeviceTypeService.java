package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoDeviceTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 设备类型
 * @date 2020-08-29 16:19:14
 */
public interface InfoDeviceTypeService extends IService<InfoDeviceTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoDeviceTypeEntity getDetailById(Long id);

    List<InfoDeviceTypeEntity> getAll();

    void save(InfoDeviceTypeEntity infoDeviceTypeEntity, Long userId);

    void updateById(InfoDeviceTypeEntity infoDeviceTypeEntity, Long userId);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);
}

